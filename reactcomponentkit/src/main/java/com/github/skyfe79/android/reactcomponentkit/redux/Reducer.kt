package com.github.skyfe79.android.reactcomponentkit.redux

typealias Reducer<STATE> = (Action) -> STATE
typealias Reducer2<STATE> = (STATE) -> STATE