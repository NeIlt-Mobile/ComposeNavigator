package neilt.mobile.section_navigation_compose.ui.screens.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import neilt.mobile.core.navigation.Navigator
import neilt.mobile.section_navigation_compose.ui.navigation.AndroidDestination

class RootViewModel(val navigator: Navigator) : ViewModel() {

    fun navigateTo(destination: AndroidDestination) {
        viewModelScope.launch {
            navigator.navigateTo(destination)
        }
    }
}
