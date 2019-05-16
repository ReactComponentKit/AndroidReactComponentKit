package com.github.skyfe79.android.reactcomponentkit.viewmodel

import android.app.Application
import com.github.skyfe79.android.reactcomponentkit.redux.AndroidViewModelType
import com.github.skyfe79.android.reactcomponentkit.redux.State

class RootAndroidViewModelType<S: State>(application: Application): AndroidViewModelType<S>(application) {

}