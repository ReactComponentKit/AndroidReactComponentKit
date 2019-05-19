package com.github.skyfe79.android.library.app

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.skyfe79.android.library.app.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.rx.AutoDisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.DisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainViewLayout(val viewModel: MainViewModel): AnkoComponent<MainActivity> {

    lateinit var countTextView: TextView
    lateinit var actionButton: Button
    private val disposeBag = AutoDisposeBag()

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        val layout = relativeLayout {
            val ID_TEXTVIEW = 1

            countTextView = textView("${viewModel.output.count.value}")
            actionButton = button("Click Me")

            countTextView.lparams(width = wrapContent) {
                id = ID_TEXTVIEW
                centerInParent()
            }

            actionButton.lparams(wrapContent) {
                topMargin = dip(10)
                below(ID_TEXTVIEW)
                centerHorizontally()
            }
        }

        return layout
    }

    fun setupViewModelOutputs() {
        actionButton.onClick {
            viewModel.dispatch(IncreaseAction())
        }

        viewModel.output.count
            .subscribe {
                countTextView.text = "COUNT: $it"
            }
            .disposedBy(disposeBag)
    }

    fun bindTo(lifecycleOwner: LifecycleOwner) {
        disposeBag.bindTo(lifecycleOwner)
    }
}