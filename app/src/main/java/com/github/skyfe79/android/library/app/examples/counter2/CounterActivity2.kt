package com.github.skyfe79.android.library.app.examples.counter

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.rx.AutoDisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView

class CounterActivity2: AppCompatActivity() {
    private val disposeBag = AutoDisposeBag(this)
    private lateinit var viewModel: CounterViewModel2
    private val layout2: CounterLayout2 by lazy {
        CounterLayout2(viewModel.token, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CounterViewModel2::class.java)
        layout2.setContentView(this)

        handleUserActions()
        handleViewModelOutput()
    }

    private fun handleUserActions() {
        layout2.increaseButton.onClick {
            viewModel.dispatch(IncreaseAction())
        }

        layout2.decreaseButton.onClick {
            viewModel.dispatch(DecreaseAction())
        }
    }

    private fun handleViewModelOutput() {
        viewModel
            .count
            .asObservable()
            .subscribe {
                layout2.countTextView.text = "$it"
            }
            .disposedBy(disposeBag)
    }
}
