# Navigation Core Library

A Kotlin-based library providing type-safe navigation tools and abstractions for Android applications. This library simplifies navigation logic using type-safe destinations and integrates seamlessly with `Jetpack Navigation` and `Kotlin Serialization`.

---

## Features

- **Type-safe navigation**: Avoid hardcoded strings by defining destinations as strongly-typed objects.
- **Composable integration**: Compatible with Jetpack Compose navigation.
- **Built-in support for `Kotlin Serialization`**: Simplify argument serialization and deserialization.
- **Customizable navigation options**: Extend and customize navigation behavior with minimal effort.
- **Integration-ready**: Compatible with dependency injection frameworks like `Koin`.

---

## Installation

This library is not currently published to a repository like Maven Central. To use it, clone the project and include the library module in your Android project.

---

## Getting Started

### 1. Define Destinations
Define destinations in your app as `sealed interfaces` or `data objects`.

```kotlin
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
```

### 2. Implement Navigator
Use the provided `AndroidNavigator` class to handle navigation logic.

```kotlin
val navigationModule = module {
    single { AndroidNavigator(AndroidDestination.MainSection) } bind Navigator::class
}
```

### 3. Integrate with Jetpack Compose
Bind your navigation actions to a `NavHost` within a Composable.

```kotlin
@Composable
fun RootContent(viewModel: RootViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val navigator = viewModel.navigator

    Scaffold(
        bottomBar = { BottomNavigationBar(viewModel.navigationItems, navController) }
    ) { innerPadding ->
        ObserveNavigationEvents(navigator, navController)
        NavHost(
            navController = navController,
            startDestination = navigator.startDestination
        ) {
            navigation<AndroidDestination.MainSection>(
                startDestination = AndroidDestination.MainSection.HomeScreen
            ) {
                composable<AndroidDestination.MainSection.HomeScreen> { HomeScreen() }
                composable<AndroidDestination.MainSection.ExploreScreen> { ExploreScreen() }
                composable<AndroidDestination.MainSection.ProfileScreen> { ProfileScreen() }
            }
        }
    }
}

@Composable
private fun ObserveNavigationEvents(navigator: Navigator, navController: NavController) {
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.NavigateTo -> {
                navController.navigate(action.destination) {
                    action.navOptions(this)
                }
            }

            is NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
}
```

---

## Usage Example

```kotlin
class RootViewModel(private val navigator: Navigator) : ViewModel() {
    fun navigateTo(destination: AndroidDestination) {
        viewModelScope.launch {
            navigator.navigateTo(destination)
        }
    }
}
```

Trigger navigation by calling:

```kotlin
viewModel.navigateTo(AndroidDestination.MainSection.HomeScreen)
```

---

## Example Project

For a practical implementation of this library, refer to the PixivCompose project. This repository demonstrates how to integrate and use the Navigation Core Library in a real-world application.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
