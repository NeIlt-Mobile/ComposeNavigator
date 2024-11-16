package neilt.mobile.section_navigation_compose.ui.screens.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import neilt.mobile.core.navigation.NavigationAction
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.section_navigation_compose.ui.components.navigation.BottomNavigationBar
import neilt.mobile.section_navigation_compose.ui.navigation.AndroidDestination
import neilt.mobile.section_navigation_compose.ui.screens.explore.ExploreScreen
import neilt.mobile.section_navigation_compose.ui.screens.home.HomeScreen
import neilt.mobile.section_navigation_compose.ui.screens.profile.ProfileScreen
import neilt.mobile.section_navigation_compose.ui.theme.AppTheme
import neilt.mobile.section_navigation_compose.ui.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootContent(
    viewModel: RootViewModel = koinViewModel(),
) {
    AppTheme {
        val navController = rememberNavController()
        val navigator = viewModel.navigator

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    navItems = viewModel.navigationItems,
                    navController = navController
                )
            }
        ) { innerPadding ->
            ObserveNavigationEvents(navigator, navController)
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = navigator.startDestination
            ) {
                navigation<AndroidDestination.MainSection>(
                    startDestination = AndroidDestination.MainSection.HomeScreen
                ) {
                    composable<AndroidDestination.MainSection.HomeScreen> { HomeScreen() }
                    composable<AndroidDestination.MainSection.ExploreScreen> { ExploreScreen() }
                    composable<AndroidDestination.MainSection.ProfileScreen> { ProfileScreen() }
                }
            }
        }
    }
}

@Composable
private fun ObserveNavigationEvents(navigator: Navigator, navController: NavController) {
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.NavigateTo -> {
                navController.navigate(action.destination) {
                    action.navOptions(this)
                }
            }

            is NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
}
