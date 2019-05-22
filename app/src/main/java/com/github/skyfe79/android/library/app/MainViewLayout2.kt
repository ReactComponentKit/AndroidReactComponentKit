package com.github.skyfe79.android.library.app

import android.arch.lifecycle.LifecycleOwner
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.skyfe79.android.library.app.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.rx.AutoDisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.include
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainViewLayout2(val viewModel: MainViewModel): AnkoComponent<MainActivity> {

    lateinit var countTextView: TextView
    lateinit var actionButton: Button
    private val disposeBag = AutoDisposeBag()

    override fun createView(ui: AnkoContext<MainActivity>): View {
        val view = ui.include<View>(R.layout.activity_main)
        actionButton = view.findViewById(R.id.button)
        countTextView = view.findViewById(R.id.textView)

        return view
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