package com.github.skyfe79.android.reactcomponentkit.redux

import io.reactivex.Observable

typealias Middleware = (State, Action) -> Observable<State>