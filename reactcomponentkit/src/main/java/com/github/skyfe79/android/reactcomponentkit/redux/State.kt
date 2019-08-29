package com.github.skyfe79.android.reactcomponentkit.redux


interface StateCopyable <S> {
    fun copyState(): S
}

abstract class State: StateCopyable<State> {
    var error: Error? = null
}

