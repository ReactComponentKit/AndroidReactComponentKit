package com.github.skyfe79.android.reactcomponentkit.rx

import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.viewmodel.RCKViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.rxkotlin.subscribeBy

fun <S: State, T: Any> Single<T>.execute(success: (T) -> S): Observable<S> {
    return Single.create<S> { emitter ->
        this.subscribeBy(
            onSuccess = {
                emitter.onSuccess(success(it))
            },
            onError = {
                emitter.onError(it)
            }
        )
    }.toObservable()
}

fun <S: State, T: Any> Single<T>.execute(success: (T) -> S, error: SingleEmitter<S>.(Throwable) -> Unit): Observable<S> {
    return Single.create<S> { emitter ->
        this.subscribeBy(
            onSuccess = {
                emitter.onSuccess(success(it))
            },
            onError = {
                error(emitter, it)
            }
        )
    }.toObservable()
}

fun <S: State, T: Any> Single<T>.execute(viewModel: RCKViewModel<S>, success: S.(T) -> S): Observable<S> {
    return Single.create<S> { emitter ->
        this.subscribeBy(
            onSuccess = {
                viewModel.withState { state ->
                    emitter.onSuccess(success(state, it))
                }
            },
            onError = {
                emitter.onError(it)
            }
        )
    }.toObservable()
}

fun <S: State, T: Any> Single<T>.execute(viewModel: RCKViewModel<S>, success: S.(T) -> S, error: SingleEmitter<S>.(Throwable) -> Unit): Observable<S> {
    return Single.create<S> { emitter ->
        this.subscribeBy(
            onSuccess = {
                viewModel.withState { state ->
                    emitter.onSuccess(success(state, it))
                }
            },
            onError = {
                error(emitter, it)
            }
        )
    }.toObservable()
}