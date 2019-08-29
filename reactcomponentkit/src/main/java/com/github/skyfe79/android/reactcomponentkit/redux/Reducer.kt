package com.github.skyfe79.android.reactcomponentkit.redux

typealias Reducer<STATE, ACTION> = (STATE, ACTION) -> STATE?