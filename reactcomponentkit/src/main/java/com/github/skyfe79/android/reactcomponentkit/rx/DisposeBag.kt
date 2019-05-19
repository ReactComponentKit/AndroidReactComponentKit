package com.github.skyfe79.android.reactcomponentkit.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposeBag {
    private var disposables: CompositeDisposable = CompositeDisposable()

    fun add(disposable: Disposable) {
        if (disposable.isDisposed) return
        disposables.add(disposable)
    }

    fun dispose() {
        if (disposables.isDisposed) return
        disposables.dispose()
    }
}

fun Disposable.disposedBy(disposeBag: DisposeBag) {
    disposeBag.add(this)
}