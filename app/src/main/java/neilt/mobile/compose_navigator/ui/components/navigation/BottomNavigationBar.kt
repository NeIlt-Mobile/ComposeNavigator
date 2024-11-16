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

package neilt.mobile.compose_navigator.ui.components.navigation

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
