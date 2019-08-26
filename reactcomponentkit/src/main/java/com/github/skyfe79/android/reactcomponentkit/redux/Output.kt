package com.github.skyfe79.android.reactcomponentkit.redux

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class Output<T>(defaultValue: T?) {
    private val behaviorRelay = if (defaultValue != null) BehaviorRelay.createDefault(defaultValue) else BehaviorRelay.create()

    val value: T?
        get() = behaviorRelay.value

    fun accept(value: T, withoutCompare: Boolean = false): ResetChanin {
        if (withoutCompare) {
            behaviorRelay.accept(value)
        } else {
            val currentValue = behaviorRelay.value
            if (currentValue != value) {
                behaviorRelay.accept(value)
            }
        }
        return ResetChanin()
    }

    fun asObservable(): Observable<T> {
        return behaviorRelay.observeOn(AndroidSchedulers.mainThread())
    }

    inner class ResetChanin {
        fun afterReset(value: T) {
            accept(value, withoutCompare = true)
        }
    }
}