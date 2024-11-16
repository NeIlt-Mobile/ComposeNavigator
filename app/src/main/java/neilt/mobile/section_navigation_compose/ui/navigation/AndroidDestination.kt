package neilt.mobile.section_navigation_compose.ui.navigation

import kotlinx.serialization.Serializable
import neilt.mobile.core.navigation.Destination

sealed interface AndroidDestination : Destination {

    @Serializable
    data object MainSection : AndroidDestination {

        @Serializable
        data object HomeScreen : AndroidDestination

        @Serializable
        data object ExploreScreen : AndroidDestination

        @Serializable
        data object ProfileScreen : AndroidDestination
    }
}
