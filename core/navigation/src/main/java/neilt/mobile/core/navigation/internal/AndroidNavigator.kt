package neilt.mobile.core.navigation.internal

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.receiveAsFlow
import neilt.mobile.core.navigation.Destination
import neilt.mobile.core.navigation.NavOptions
import neilt.mobile.core.navigation.NavigationAction
import neilt.mobile.core.navigation.Navigator

internal class AndroidNavigator(override val startDestination: Destination) : Navigator {

    private val _navigationActions = Channel<NavigationAction>(Channel.RENDEZVOUS)
    override val navigationActions = _navigationActions.receiveAsFlow()

    private var lastAction: NavigationAction? = null

    private inline fun handleAction(action: NavigationAction, block: (NavigationAction) -> Unit) {
        if (action != lastAction) {
            lastAction = action.also(block)
        }
    }

    override suspend fun navigateTo(destination: Destination, navOptions: NavOptions) {
        handleAction(NavigationAction.NavigateTo(destination, navOptions)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to enqueue navigation action: $error")
            }
        }
    }

    override suspend fun navigateUp() {
        handleAction(NavigationAction.NavigateUp) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to enqueue navigate up action: $error")
            }
        }
    }
}
