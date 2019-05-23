package com.github.skyfe79.android.library.app.examples.counter

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.github.skyfe79.android.library.app.examples.counter.action.DecreaseAction
import com.github.skyfe79.android.library.app.examples.counter.action.IncreaseAction
import com.github.skyfe79.android.reactcomponentkit.component.LayoutComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CounterLayout2(token: Token, receiveState: Boolean): LayoutComponent<CounterActivity2>(token, receiveState) {

    private enum class IDs {
        TextView
    }

    lateinit var countTextView: TextView
    lateinit var increaseButton: Button
    lateinit var decreaseButton: Button

    override fun createView(ui: AnkoContext<CounterActivity2>): View = with(ui) {
        val view = relativeLayout {
            countTextView = textView("0") {
                textSize = 50f
            }
            .lparams {
                id = IDs.TextView.ordinal
                centerInParent()
            }

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER

                decreaseButton = button("   -   ").lparams(width = 0, weight = 1f)
                increaseButton = button("   +   ").lparams(width = 0, weight = 1f)

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


        increaseButton.onClick {
            dispatch(IncreaseAction())
        }

        decreaseButton.onClick {
            dispatch(DecreaseAction())
        }

        return view
    }

    override fun on(state: State) {
        val countState = (state as? CounterState) ?: return
        countTextView.text = "${countState.count}"
    }
}