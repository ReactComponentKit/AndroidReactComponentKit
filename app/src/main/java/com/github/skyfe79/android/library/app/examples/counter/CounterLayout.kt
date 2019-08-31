package com.github.skyfe79.android.library.app.examples.counter

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.github.skyfe79.android.library.app.examples.counter.action.AsyncIncreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.component.LayoutComponent
import com.github.skyfe79.android.reactcomponentkit.dispatcher.dispatch
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Async
import com.github.skyfe79.android.reactcomponentkit.redux.State
import com.github.skyfe79.android.reactcomponentkit.subscriber.subscribeState
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CounterLayout(token: Token): LayoutComponent<CounterActivity>(token) {

    private enum class IDs {
        TextView
    }

    lateinit var countTextView: TextView
    private lateinit var increaseButton: Button
    private lateinit var decreaseButton: Button
    private lateinit var asyncIncreaseButton: Button
    private lateinit var progress: ProgressBar

    override fun onInit() {
        subscribeState()
    }

    override fun createView(ui: AnkoContext<CounterActivity>): View = with(ui) {
        val view = relativeLayout {
            countTextView = textView {
                textSize = 50f
            }
            .lparams {
                id = IDs.TextView.ordinal
                centerInParent()
            }

            progress = progressBar().lparams {
                centerInParent()
            }
            progress.visibility = View.GONE

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                decreaseButton = button("   -   ").lparams(width = 0, weight = 1f)
                increaseButton = button("   +   ").lparams(width = 0, weight = 1f)
                asyncIncreaseButton = button("async +").lparams(width = 0, weight = 1f)
            }.applyRecursively {
                when(it) {
                    is Button -> it.textSize = 20f
                }
            }.lparams {
                width = matchParent
                margin = dip(10)

                alignParentBottom()
            }
        }


        asyncIncreaseButton.setOnClickListener {
            dispatch(AsyncIncreaseAction())
        }

        increaseButton.setOnClickListener {
            dispatch(IncreaseAction())
        }

        decreaseButton.setOnClickListener {
            dispatch(DecreaseAction())
        }

        return view
    }

    override fun on(state: State) {
        val countState = (state as? CounterState) ?: return
        countTextView.text = "${countState.count}"

        when (state.asyncCount) {
            is Async.Loading -> {
                progress.visibility = View.VISIBLE
                progress.animate()
            }
            is Async.Success -> {
                progress.visibility = View.GONE
            }
        }
    }
}