package com.github.skyfe79.android.library.app.examples.counter.action

import com.github.skyfe79.android.reactcomponentkit.redux.Action

data class IncreaseAction(val payload: Int = 1): Action
data class DecreaseAction(val payload: Int = 1): Action
