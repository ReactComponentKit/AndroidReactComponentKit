package com.github.skyfe79.android.reactcomponentkit.redux

import io.reactivex.Observable

typealias Reducer = (State, Action) -> Observable<State>