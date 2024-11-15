package neilt.mobile.core.navigation

sealed interface NavigationAction {

    data class NavigateTo(
        val destination: Destination,
        val navOptions: NavOptions = {},
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}
