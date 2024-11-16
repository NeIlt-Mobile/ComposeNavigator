package neilt.mobile.section_navigation_compose.ui.components.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import neilt.mobile.core.navigation.extensions.hasDestination

@Composable
fun BottomNavigationBar(
    navItems: List<BottomNavigationItem>,
    navController: NavController,
) {
    require(navItems.size in 2..5) { "The number of navigation items must be between 2 and 5." }

    val currentBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = remember { derivedStateOf { currentBackEntry?.destination } }

    BottomAppBar {
        navItems.forEach { item: BottomNavigationItem ->
            val isSelected = currentDestination.value.hasDestination(item.destination)

            NavigationBarItem(
                selected = isSelected,
                label = item.label,
                icon = {
                    Crossfade(
                        targetState = isSelected,
                        label = "AnimateIconChange"
                    ) { target -> if (target) item.iconSelected() else item.iconUnselected() }
                },
                onClick = {
                    if (!isSelected) item.onClick()
                }
            )
        }
    }
}
