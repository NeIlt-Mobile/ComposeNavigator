package neilt.mobile.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import neilt.mobile.core.navigation.internal.AndroidNavigator

interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigateTo(destination: Destination, navOptions: NavOptions = {})
    suspend fun navigateUp()
}

@Composable
fun rememberNavigator(startDestination: Destination): Navigator {
    return remember { AndroidNavigator(startDestination = startDestination) }
}
