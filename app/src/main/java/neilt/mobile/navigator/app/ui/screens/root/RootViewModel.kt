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
import neilt.mobile.navigator.Navigator
import neilt.mobile.navigator.app.R
import neilt.mobile.navigator.app.ui.components.navigation.BottomNavigationItem
import neilt.mobile.navigator.app.ui.navigation.AndroidDestination

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
