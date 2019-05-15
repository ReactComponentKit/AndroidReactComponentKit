package com.github.skyfe79.android.reactcomponentkit.redux

import io.reactivex.Observable

typealias Postware = (State, Action) -> Observable<State>