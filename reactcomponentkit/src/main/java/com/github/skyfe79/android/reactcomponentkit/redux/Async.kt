package com.github.skyfe79.android.reactcomponentkit.redux

// Ideas from MvRx
sealed class Async<out T> {
    object Uninitialized: Async<Nothing>()
    object Loading: Async<Nothing>()
    data class Success<out T>(val value: T): Async<T>()
    data class Failed<out T>(val error: Throwable): Async<T>()
}

