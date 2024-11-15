package neilt.mobile.core.navigation.internal

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import neilt.mobile.core.navigation.Destination
import neilt.mobile.core.navigation.NavOptions
import neilt.mobile.core.navigation.NavigationAction
import neilt.mobile.core.navigation.Navigator

internal class AndroidNavigator(override val startDestination: Destination) : Navigator {

    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigateTo(
        destination: Destination,
        navOptions: NavOptions,
    ) {
        _navigationActions.send(
            NavigationAction.NavigateTo(
                destination = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}
