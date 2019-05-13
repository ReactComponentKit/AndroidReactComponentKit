package com.github.skyfe79.android.reactcomponentkit

import io.reactivex.Observable

typealias Postware = (State, Action) -> Observable<State>