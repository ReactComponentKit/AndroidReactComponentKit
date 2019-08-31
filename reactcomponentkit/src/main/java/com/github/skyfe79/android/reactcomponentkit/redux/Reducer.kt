package com.github.skyfe79.android.reactcomponentkit.redux

/**
 * Reducer type.
 * Why returns optional STATE?.
 * If you define asyncReducer in a flow, asyncFlow makes a Reducer that returns null state
 * not to update current state.
 */
typealias Reducer<STATE, ACTION> = (STATE, ACTION) -> STATE?