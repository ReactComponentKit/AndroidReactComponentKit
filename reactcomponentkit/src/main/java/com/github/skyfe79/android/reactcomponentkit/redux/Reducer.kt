package com.github.skyfe79.android.reactcomponentkit.redux

import io.reactivex.Observable

/**
 * Reducer type.
 * Why returns optional STATE?.
 * If you define asyncReducer in a flow, asyncFlow makes a Reducer that returns null state
 * not to update current state.
 */
typealias Reducer<STATE, ACTION> = (STATE, ACTION) -> STATE?
typealias AsyncReducer<STATE, ACTION> = (ACTION) -> Observable<STATE>