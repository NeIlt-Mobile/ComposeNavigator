package neilt.mobile.core.navigation.extensions

import android.annotation.SuppressLint
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import neilt.mobile.core.navigation.Destination

/**
 * Extension function for [NavDestination] to check if the destination or any destination in its hierarchy
 * matches a specific [Destination] type.
 *
 * @param T The type of [Destination] to check for.
 * @return `true` if the destination or any of its parents in the hierarchy matches the given [Destination] type,
 *         `false` otherwise.
 */
inline fun <reified T : Destination> NavDestination?.hasDestination(): Boolean {
    return this?.hierarchy?.any { it.hasRoute<T>() } == true
}

/**
 * Checks if the current [NavDestination] or any destination in its hierarchy matches the specified [Destination].
 *
 * This method uses the class of the given [Destination] to determine a match.
 *
 * @param destination The [Destination] to check for.
 * @return `true` if the destination or any of its parents in the hierarchy matches the given [Destination],
 *         `false` otherwise.
 */
@SuppressLint("RestrictedApi") // Suppresses the lint warning for using restricted APIs
fun NavDestination?.hasDestination(destination: Destination): Boolean {
    return this?.hierarchy?.any { it.hasRoute(destination::class) } == true
}
