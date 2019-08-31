package com.github.skyfe79.android.reactcomponentkit.component

import android.app.Service
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token

abstract class ServiceComponent(token: Token): Service(), ReactComponent {
    override var token: Token = token

    init {
        this.onInit()
    }

    open fun onInit() = Unit
}