package com.github.skyfe79.android.reactcomponentkit.dispatcher

import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.component.*
import com.github.skyfe79.android.reactcomponentkit.redux.Action


fun FragmentComponent.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun <T> LayoutComponent<T>.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun ViewComponent.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun ServiceComponent.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun IntentServiceComponent.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}