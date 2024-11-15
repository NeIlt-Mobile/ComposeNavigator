package neilt.mobile.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import neilt.mobile.core.navigation.internal.AndroidNavigator

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
 * Composable function for remembering a [Navigator] instance.
 *
 * @param startDestination The initial [Destination].
 * @return A remembered [Navigator] instance.
 */
@Composable
fun rememberNavigator(startDestination: Destination): Navigator {
    return remember { AndroidNavigator(startDestination = startDestination) }
}
