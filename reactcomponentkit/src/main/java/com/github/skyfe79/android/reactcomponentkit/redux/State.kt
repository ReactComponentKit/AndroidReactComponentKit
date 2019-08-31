package com.github.skyfe79.android.reactcomponentkit.redux

/**
 * Utility interface for copying state.
 * It is needed because abstract class State doesn't know how to copy subclass.
 */
interface StateCopyable <S> {
    fun copyState(): S
}

abstract class State: StateCopyable<State> {
    var error: Error? = null
}