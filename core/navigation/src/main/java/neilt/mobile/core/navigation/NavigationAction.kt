package neilt.mobile.core.navigation

/**
 * Represents navigation actions that can be performed within the navigation system.
 */
sealed interface NavigationAction {

    /**
     * Navigation action for navigating to a specific [Destination].
     *
     * @property destination The target [Destination].
     * @property navOptions Additional navigation options, if any.
     */
    data class NavigateTo(
        val destination: Destination,
        val navOptions: NavOptions = {},
    ) : NavigationAction

    /**
     * Navigation action for navigating up in the navigation stack.
     */
    data object NavigateUp : NavigationAction
}
