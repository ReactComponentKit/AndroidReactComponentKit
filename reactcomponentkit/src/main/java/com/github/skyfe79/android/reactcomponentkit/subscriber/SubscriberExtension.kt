package com.github.skyfe79.android.reactcomponentkit.subscriber

import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.component.*
import com.github.skyfe79.android.reactcomponentkit.redux.Action


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

fun ServiceComponent.subscribeState() {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.registerSubscriber(this) }
}

fun IntentServiceComponent.subscribeState() {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.registerSubscriber(this) }
}