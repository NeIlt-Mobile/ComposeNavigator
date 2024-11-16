package neilt.mobile.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Interface for defining navigation behavior within an application.
 *
 * @property startDestination The initial [Destination].
 * @property navigationActions A flow of [NavigationAction] events.
 */
interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    /**
     * Navigates to the specified destination with the given [NavOptions].
     *
     * @param destination The target [Destination].
     * @param navOptions Additional navigation options.
     */
    suspend fun navigateTo(destination: Destination, navOptions: NavOptions = {})

    /**
     * Navigates up in the navigation stack.
     */
    suspend fun navigateUp()
}

/**
 * Implementation of [Navigator] for Android platform.
 *
 * This class handles navigation actions and provides a flow of [NavigationAction] events.
 *
 * @property startDestination The starting destination for the navigation.
 */
class AndroidNavigator(override val startDestination: Destination) : Navigator {

    private val _navigationActions = Channel<NavigationAction>(Channel.RENDEZVOUS)
    override val navigationActions = _navigationActions.receiveAsFlow()

    private var lastAction: NavigationAction? = null

    /**
     * Handles navigation actions, ensuring that duplicate actions are not processed.
     *
     * @param action The navigation action to handle.
     * @param block The block to execute with the given action.
     */
    private inline fun handleAction(action: NavigationAction, block: (NavigationAction) -> Unit) {
        if (action != lastAction) {
            lastAction = action.also(block)
        }
    }

    /**
     * Navigates to the specified destination with the given [NavOptions].
     *
     * @param destination The target [Destination].
     * @param navOptions Additional navigation options.
     */
    override suspend fun navigateTo(destination: Destination, navOptions: NavOptions) {
        handleAction(NavigationAction.NavigateTo(destination, navOptions)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to enqueue navigation action: $error")
            }
        }
    }

    /**
     * Navigates up in the navigation stack.
     */
    override suspend fun navigateUp() {
        handleAction(NavigationAction.NavigateUp) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to enqueue navigate up action: $error")
            }
        }
    }
}


/**
 * Composable function for remembering a [Navigator] instance.
 *
 * @param startDestination The initial [Destination].
 * @return A remembered [Navigator] instance.
 */
@Composable
fun rememberNavigator(startDestination: Destination): Navigator {
    return remember { AndroidNavigator(startDestination = startDestination) }
}
