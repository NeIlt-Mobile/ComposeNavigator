package neilt.mobile.section_navigation_compose.ui.screens.root

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.section_navigation_compose.R
import neilt.mobile.section_navigation_compose.ui.components.navigation.BottomNavigationItem
import neilt.mobile.section_navigation_compose.ui.navigation.AndroidDestination

class RootViewModel(val navigator: Navigator) : ViewModel() {

    val navigationItems = listOf(
        BottomNavigationItem(
            destination = AndroidDestination.MainSection.HomeScreen,
            label = {
                Text(text = stringResource(R.string.navigation_home))
            },
            iconSelected = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.navigation_home)
                )
            },
            iconUnselected = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(R.string.navigation_home)
                )
            },
            onClick = { navigateTo(AndroidDestination.MainSection.HomeScreen) },
        ),
        BottomNavigationItem(
            destination = AndroidDestination.MainSection.ExploreScreen,
            label = {
                Text(text = stringResource(R.string.navigation_explore))
            },
            iconSelected = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.navigation_home)
                )
            },
            iconUnselected = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.navigation_home)
                )
            },
            onClick = { navigateTo(AndroidDestination.MainSection.ExploreScreen) },
        ),
        BottomNavigationItem(
            destination = AndroidDestination.MainSection.ProfileScreen,
            label = {
                Text(text = stringResource(R.string.navigation_profile))
            },
            iconSelected = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.navigation_home)
                )
            },
            iconUnselected = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = stringResource(R.string.navigation_home)
                )
            },
            onClick = { navigateTo(AndroidDestination.MainSection.ProfileScreen) },
        ),
    )

    fun navigateTo(destination: AndroidDestination) {
        viewModelScope.launch {
            navigator.navigateTo(destination)
        }
    }
}
