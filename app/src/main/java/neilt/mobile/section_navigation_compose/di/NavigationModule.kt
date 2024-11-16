package neilt.mobile.section_navigation_compose.di

import neilt.mobile.core.navigation.AndroidNavigator
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.section_navigation_compose.ui.navigation.AndroidDestination
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    single { AndroidNavigator(AndroidDestination.MainSection) } bind Navigator::class
}
