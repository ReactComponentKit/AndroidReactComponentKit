package com.github.skyfe79.android.reactcomponentkit.redux

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

class Output<T>(defaultValue: T?, private val ignoreCompareValue: Boolean = false) {
    private val behaviorRelay = if (defaultValue != null) BehaviorRelay.createDefault(defaultValue) else BehaviorRelay.create()

    val value: T?
        get() = behaviorRelay.value

    fun accept(value: T) {
        if (ignoreCompareValue) {
            behaviorRelay.accept(value)
        } else {
            val currentValue = behaviorRelay.value
            if (currentValue != null) {
                if (currentValue != value) {
                    behaviorRelay.accept(value)
                }
            } else {
                behaviorRelay.accept(value)
            }
        }
    }

    fun asObservable(): Observable<T> {
        return behaviorRelay
    }
}