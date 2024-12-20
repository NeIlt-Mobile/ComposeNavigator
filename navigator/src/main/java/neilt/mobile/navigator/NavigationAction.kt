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

/**
 * Represents navigation actions that can be performed within the navigation system.
 */
sealed interface NavigationAction {

    /**
     * Navigation action for navigating to a specific [Destination].
     *
     * @property destination The target [Destination].
     * @property navOptions Additional navigation options, if any.
     */
    data class NavigateTo(
        val destination: Destination,
        val navOptions: NavOptions = {},
    ) : NavigationAction

    /**
     * Navigation action for navigating up in the navigation stack.
     */
    data object NavigateUp : NavigationAction

    /**
     * Navigation action to set a new root destination, clearing all previous destinations.
     *
     * @property destination The [Destination] to set as the new root.
     */
    data class SetRoot(val destination: Destination) : NavigationAction

    /**
     * Navigation action to replace the current destination with a new one.
     *
     * @property destination The [Destination] to replace the current one with.
     */
    data class Replace(val destination: Destination) : NavigationAction

    /**
     * Navigation action to navigate back to a specific destination in the stack.
     *
     * @property destination The [Destination] to navigate back to, or `null` to clear the stack.
     */
    data class BackTo(val destination: Destination?) : NavigationAction

    /**
     * Navigation action to start a new chain of destinations, replacing the current stack.
     *
     * @property destinations A list of [Destination] objects for the new navigation chain.
     */
    data class StartChain(val destinations: List<Destination>) : NavigationAction

    /**
     * Navigation action to reset the navigation stack with a new chain of destinations.
     *
     * @property destinations A list of [Destination] objects for the new navigation chain.
     */
    data class ResetChain(val destinations: List<Destination>) : NavigationAction

    /**
     * Navigation action to finish the current navigation flow.
     */
    data object Finish : NavigationAction
}
