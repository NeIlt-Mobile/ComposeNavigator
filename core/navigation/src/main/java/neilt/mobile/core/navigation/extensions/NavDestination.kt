package neilt.mobile.core.navigation.extensions

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import neilt.mobile.core.navigation.Destination

inline fun <reified T : Destination> NavDestination?.hasDestination(): Boolean {
    return this?.hierarchy?.any { it.hasRoute<T>() } == true
}
