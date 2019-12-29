package com.example.weatherapp.navigation

import androidx.compose.Model
import java.util.*

@Model
object Navigation {
    private val screenStack: Deque<Screen>
    // Guess that I have to write to the field to trigger the recompose, otherwise it could a
    // computed property
    var currentScreen: Screen
        private set

    init {
        screenStack = ArrayDeque<Screen>().apply { add(
            Screen.List
        ) }
        currentScreen = screenStack.first
    }

    fun navigateTo(screen: Screen) {
        screenStack.push(screen)
        currentScreen = screenStack.first
    }

    fun back(): Boolean {
        var hasReachedRoot = true
        if (screenStack.size > 1) {
            screenStack.pop()
            hasReachedRoot = false
        }
        currentScreen = screenStack.first

        return hasReachedRoot
    }
}
