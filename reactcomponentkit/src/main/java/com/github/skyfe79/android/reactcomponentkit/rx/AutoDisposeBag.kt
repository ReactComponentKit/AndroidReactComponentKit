package com.github.skyfe79.android.reactcomponentkit.rx

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposeBag(lifecycleOwner: LifecycleOwner): LifecycleObserver {
    private var disposables: CompositeDisposable = CompositeDisposable()

    init {
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