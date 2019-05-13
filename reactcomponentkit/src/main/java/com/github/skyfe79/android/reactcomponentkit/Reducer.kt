package com.github.skyfe79.android.reactcomponentkit

import io.reactivex.Observable

typealias Reducer = (State, Action) -> Observable<State>