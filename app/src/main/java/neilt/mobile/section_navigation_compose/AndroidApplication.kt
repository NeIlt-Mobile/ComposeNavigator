package neilt.mobile.section_navigation_compose

import android.app.Application
import neilt.mobile.section_navigation_compose.di.navigationModule
import neilt.mobile.section_navigation_compose.di.viewModelModule
import org.koin.core.context.startKoin

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                navigationModule,
                viewModelModule,
            )
        }
    }
}
