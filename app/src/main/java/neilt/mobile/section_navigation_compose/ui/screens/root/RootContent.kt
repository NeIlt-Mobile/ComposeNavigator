package neilt.mobile.section_navigation_compose.ui.screens.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import neilt.mobile.section_navigation_compose.ui.components.navigation.BottomNavigationBar
import neilt.mobile.section_navigation_compose.ui.navigation.AndroidDestination
import neilt.mobile.section_navigation_compose.ui.theme.AppTheme
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
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = navigator.startDestination
            ) {
                navigation<AndroidDestination.MainSection>(
                    startDestination = AndroidDestination.MainSection.HomeScreen
                ) {
                    composable<AndroidDestination.MainSection.HomeScreen> {}
                    composable<AndroidDestination.MainSection.ExploreScreen> {}
                    composable<AndroidDestination.MainSection.ProfileScreen> {}
                }
            }
        }
    }
}
