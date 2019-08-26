package com.github.skyfe79.android.reactcomponentkit.dispatcher

import com.github.skyfe79.android.reactcomponentkit.RCK
import com.github.skyfe79.android.reactcomponentkit.component.FragmentComponent
import com.github.skyfe79.android.reactcomponentkit.component.LayoutComponent
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import com.github.skyfe79.android.reactcomponentkit.redux.RCKViewModel


fun FragmentComponent.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun FragmentComponent.dispatch(action: (RCKViewModel<*>) -> Unit) {
    val viewModel = RCK.viewModel(token)
    viewModel?.let { action(it) }
}

fun <T> LayoutComponent<T>.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun <T> LayoutComponent<T>.dispatch(action: (RCKViewModel<*>) -> Unit) {
    val viewModel = RCK.viewModel(token)
    viewModel?.let { action(it) }
}

fun ViewComponent.dispatch(action: Action) {
    val viewModel = RCK.viewModel(token)
    viewModel.let { it?.dispatch(action) }
}

fun ViewComponent.dispatch(action: (RCKViewModel<*>) -> Unit) {
    val viewModel = RCK.viewModel(token)
    viewModel?.let { action(it) }
}