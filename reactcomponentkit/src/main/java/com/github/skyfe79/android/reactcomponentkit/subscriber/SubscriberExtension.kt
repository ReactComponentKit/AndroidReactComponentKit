package com.github.skyfe79.android.reactcomponentkit.subscriber

import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.StateSubscriber
import com.github.skyfe79.android.reactcomponentkit.component.*
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token


fun FragmentComponent.subscribeState() {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.registerSubscriber(this) }
}

fun <T> LayoutComponent<T>.subscribeState() {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.registerSubscriber(this) }
}

fun ViewComponent.subscribeState() {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.registerSubscriber(this) }
}

fun StateSubscriber.subscribeState(token: Token) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.registerSubscriber(this) }
}