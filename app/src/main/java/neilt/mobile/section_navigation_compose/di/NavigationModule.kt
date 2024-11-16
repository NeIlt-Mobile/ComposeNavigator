package neilt.mobile.section_navigation_compose.di

import neilt.mobile.core.navigation.AndroidNavigator
import neilt.mobile.core.navigation.Navigator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::AndroidNavigator) bind Navigator::class
}
