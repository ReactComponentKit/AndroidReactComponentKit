package com.github.skyfe79.android.reactcomponentkit.component

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.skyfe79.android.reactcomponentkit.ComponentDispatchEvent
import com.github.skyfe79.android.reactcomponentkit.ComponentNewStateEvent
import com.github.skyfe79.android.reactcomponentkit.ReactComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State

internal enum class FragmentComponentState {
    SHOW,
    HIDE,
    NONE
}

abstract class FragmentComponent: Fragment(), ReactComponent {

    companion object {
        const val STATE_SHOW = 1
        const val STATE_HIDE = 2

        inline fun <reified T: FragmentComponent> newInstance(token: Token, receiveState: Boolean): T {
            val args = Bundle()
            args.putParcelable("token", token)
            args.putBoolean("receiveState", receiveState)
            val component = T::class.java.newInstance()
            component.arguments = args
            return component
        }
    }
    override var token: Token = Token.empty
    override var receiveState: Boolean = false
    override var newStateEventBus: EventBus<ComponentNewStateEvent>? = null
    override var dispatchEventBus: EventBus<ComponentDispatchEvent> = EventBus(token)
    internal var state: FragmentComponentState = FragmentComponentState.NONE

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (arguments != null) {
            arguments?.let {
                this@FragmentComponent.receiveState = it.getBoolean("receiveState", false)
                this@FragmentComponent.token = (it.getParcelable("token") as? Token) ?: Token.empty
            }
        }

        newStateEventBus = if (receiveState) EventBus(token) else null
        dispatchEventBus = EventBus(token)

        newStateEventBus?.let {
            it.on { event ->
                when (event) {
                    is ComponentNewStateEvent.On -> applyNew(event.state)
                }
            }
        }

        if (state == FragmentComponentState.SHOW) {
            startEventBus()
        } else {
            stopEventBus()
        }
    }

    override fun onDetach() {
        super.onDetach()
        stopEventBus()
    }

    private fun applyNew(state: State) {
        on(state)
    }

    abstract fun on(state: State)

    internal fun startEventBus() {
        newStateEventBus?.start()
        dispatchEventBus.start()
    }

    internal fun stopEventBus() {
        newStateEventBus?.stop()
        dispatchEventBus.start()
    }
}

inline fun <reified T: FragmentComponent> FragmentActivity.fragmentComponent(token: Token, receiveState: Boolean = true): T {
    return FragmentComponent.newInstance(token, receiveState)
}

fun FragmentActivity.replaceFragment(component: FragmentComponent, containerViewId: Int, tag: String? = null) {
    component.state = FragmentComponentState.SHOW
    this.supportFragmentManager
        .beginTransaction()
        .replace(containerViewId, component, tag)
        .commit()
}

fun FragmentActivity.addFragment(component: FragmentComponent, containerViewId: Int, tag: String? = null) {
    component.state = FragmentComponentState.SHOW
    this.supportFragmentManager
        .beginTransaction()
        .add(containerViewId, component, tag)
        .commit()
}

fun FragmentActivity.hideFragment(component: FragmentComponent) {
    component.state = FragmentComponentState.HIDE
    component.stopEventBus()

    this.supportFragmentManager
        .beginTransaction()
        .hide(component)
        .commit()
}

fun FragmentActivity.showFragment(component: FragmentComponent) {
    component.state = FragmentComponentState.SHOW
    component.startEventBus()

    this.supportFragmentManager
        .beginTransaction()
        .show(component)
        .commit()
}

fun FragmentActivity.removeFragment(component: FragmentComponent) {
    component.state = FragmentComponentState.HIDE
    this.supportFragmentManager
        .beginTransaction()
        .remove(component)
        .commit()
}