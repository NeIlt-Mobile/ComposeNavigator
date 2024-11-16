package neilt.mobile.section_navigation_compose.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import neilt.mobile.core.navigation.Destination

@Stable
data class BottomNavigationItem(
    val destination: Destination,
    val label: @Composable () -> Unit,
    val iconSelected: @Composable () -> Unit,
    val iconUnselected: @Composable () -> Unit,
    val onClick: () -> Unit = {},
)