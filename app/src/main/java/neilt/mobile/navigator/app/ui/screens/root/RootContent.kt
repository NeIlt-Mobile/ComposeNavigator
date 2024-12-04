/*
 * MIT License
 *
 * Copyright (c) 2024 NeIlt-Mobile
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package neilt.mobile.navigator.app.ui.screens.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import neilt.mobile.navigator.NavigationAction
import neilt.mobile.navigator.Navigator
import neilt.mobile.navigator.app.ui.components.navigation.BottomNavigationBar
import neilt.mobile.navigator.app.ui.navigation.AndroidDestination
import neilt.mobile.navigator.app.ui.screens.explore.ExploreScreen
import neilt.mobile.navigator.app.ui.screens.home.HomeScreen
import neilt.mobile.navigator.app.ui.screens.profile.ProfileScreen
import neilt.mobile.navigator.app.ui.theme.AppTheme
import neilt.mobile.navigator.app.ui.utils.ObserveAsEvents
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
