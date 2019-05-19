package com.github.skyfe79.android.reactcomponentkit.rx

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposeBag: LifecycleObserver {
    private var disposables: CompositeDisposable = CompositeDisposable()

    fun bindTo(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun add(disposable: Disposable) {
        if (disposable.isDisposed) return
        disposables.add(disposable)
    }

    private fun dispose() {
        if (disposables.isDisposed) return
        disposables.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dispose()
    }
}

fun Disposable.disposedBy(disposeBag: AutoDisposeBag) {
    disposeBag.add(this)
}