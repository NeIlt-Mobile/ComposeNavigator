/*
 * MIT License
 *
 * Copyright (c) 2024 NeIlt-Mobile
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package neilt.mobile.navigator

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Interface for defining navigation behavior within an application.
 *
 * @property startDestination The initial [Destination].
 * @property navigationActions A flow of [NavigationAction] events.
 */
interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    /**
     * Navigates to the specified destination with the given [NavOptions].
     *
     * @param destination The target [Destination].
     * @param navOptions Additional navigation options.
     */
    suspend fun navigateTo(destination: Destination, navOptions: NavOptions = {})

    /**
     * Navigates up in the navigation stack.
     */
    suspend fun navigateUp()

    /**
     * Sets a new root destination, clearing all previous destinations in the stack.
     *
     * @param destination The [Destination] to set as the new root.
     */
    suspend fun setRoot(destination: Destination)

    /**
     * Replaces the current destination with a new one.
     *
     * @param destination The [Destination] to replace the current one with.
     */
    suspend fun replace(destination: Destination)

    /**
     * Navigates back to a specific [Destination] in the stack.
     * If the destination is not found, it clears the stack.
     *
     * @param destination The [Destination] to navigate back to, or `null` to clear the stack entirely.
     */
    suspend fun backTo(destination: Destination?)

    /**
     * Starts a new chain of destinations, replacing the current navigation stack.
     *
     * @param destinations A list of [Destination] objects representing the new navigation chain.
     */
    suspend fun startChain(destinations: List<Destination>)

    /**
     * Resets the navigation stack with a new chain of destinations.
     *
     * @param destinations A list of [Destination] objects representing the new navigation chain.
     */
    suspend fun resetChain(destinations: List<Destination>)

    /**
     * Completes the navigation flow and removes the current destination.
     */
    suspend fun finish()
}

/**
 * Implementation of [Navigator] for Android platform.
 *
 * This class handles navigation actions and provides a flow of [NavigationAction] events.
 *
 * @property startDestination The starting destination for the navigation.
 */
class AndroidNavigator(override val startDestination: Destination) : Navigator {

    private val _navigationActions = Channel<NavigationAction>(Channel.RENDEZVOUS)
    override val navigationActions = _navigationActions.receiveAsFlow()

    private var lastAction: NavigationAction? = null

    /**
     * Handles navigation actions, ensuring that duplicate actions are not processed.
     *
     * @param action The navigation action to handle.
     * @param block The block to execute with the given action.
     */
    private inline fun handleAction(action: NavigationAction, block: (NavigationAction) -> Unit) {
        if (action != lastAction) {
            lastAction = action.also(block)
        }
    }

    /**
     * Navigates to the specified destination with the given [NavOptions].
     *
     * @param destination The target [Destination].
     * @param navOptions Additional navigation options.
     */
    override suspend fun navigateTo(destination: Destination, navOptions: NavOptions) {
        handleAction(NavigationAction.NavigateTo(destination, navOptions)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to enqueue navigation action: $error")
            }
        }
    }

    /**
     * Navigates up in the navigation stack.
     */
    override suspend fun navigateUp() {
        handleAction(NavigationAction.NavigateUp) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to enqueue navigate up action: $error")
            }
        }
    }

    /**
     * Sets a new root destination, clearing all previous destinations in the stack.
     *
     * @param destination The [Destination] to set as the new root.
     */
    override suspend fun setRoot(destination: Destination) {
        handleAction(NavigationAction.SetRoot(destination)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to set root: $error")
            }
        }
    }

    /**
     * Replaces the current destination with a new one.
     *
     * @param destination The [Destination] to replace the current one with.
     */
    override suspend fun replace(destination: Destination) {
        handleAction(NavigationAction.Replace(destination)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to replace: $error")
            }
        }
    }

    /**
     * Navigates back to a specific [Destination] in the stack.
     * If the destination is not found, it clears the stack.
     *
     * @param destination The [Destination] to navigate back to, or `null` to clear the stack entirely.
     */
    override suspend fun backTo(destination: Destination?) {
        handleAction(NavigationAction.BackTo(destination)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to back to: $error")
            }
        }
    }

    /**
     * Starts a new chain of destinations, replacing the current navigation stack.
     *
     * @param destinations A list of [Destination] objects representing the new navigation chain.
     */
    override suspend fun startChain(destinations: List<Destination>) {
        handleAction(NavigationAction.StartChain(destinations)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to start chain: $error")
            }
        }
    }

    /**
     * Resets the navigation stack with a new chain of destinations.
     *
     * @param destinations A list of [Destination] objects representing the new navigation chain.
     */
    override suspend fun resetChain(destinations: List<Destination>) {
        handleAction(NavigationAction.ResetChain(destinations)) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to reset chain: $error")
            }
        }
    }

    /**
     * Completes the navigation flow and removes the current destination.
     */
    override suspend fun finish() {
        handleAction(NavigationAction.Finish) {
            _navigationActions.trySend(it).onFailure { error ->
                Log.e("AndroidNavigator", "Failed to finish chain: $error")
            }
        }
    }
}
