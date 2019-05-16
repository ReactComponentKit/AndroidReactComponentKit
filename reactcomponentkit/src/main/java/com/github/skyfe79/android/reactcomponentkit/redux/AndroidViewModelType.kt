package com.github.skyfe79.android.reactcomponentkit.redux

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.skyfe79.android.reactcomponentkit.redux.State

open class AndroidViewModelType<S: State>(application: Application): AndroidViewModel(application) {
}