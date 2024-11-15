package neilt.mobile.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import neilt.mobile.core.navigation.internal.AndroidNavigator

interface Navigator {
    val startDestination: Destination
}

@Composable
fun rememberNavigator(startDestination: Destination): Navigator {
    return remember { AndroidNavigator(startDestination = startDestination) }
}
