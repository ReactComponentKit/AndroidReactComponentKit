package com.github.skyfe79.android.library.app.examples.recyclerview.component

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.github.skyfe79.android.library.app.examples.recyclerview.model.BackgroundColorProvider
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessageProvider
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.component.component
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg

class TextMessageViewComponent(token: Token): ViewComponent(token) {
    lateinit var textView: TextView
    lateinit var rootLayout: View
    override fun layout(ui: AnkoContext<Context>): View = with(ui) {
        rootLayout = verticalLayout {
            lparams(matchParent, wrapContent)

            textView = textView {
                gravity = Gravity.CENTER
            }.lparams(matchParent, dip(50))
        }
        return rootLayout
    }

    override fun on(state: State) {
        val item = (state as? TextMessageProvider) ?: return
        updateTextView(item)
    }

    override fun on(item: ItemModel, position: Int) {
        val textMessage = (item as? TextMessageProvider) ?: return
        updateTextView(textMessage)
    }

    private fun updateTextView(textMessage: TextMessageProvider) {
        textView.text = textMessage.message
    }
}

class SectionViewComponent(token: Token): ViewComponent(token) {
    private var textMessageViewComponent = TextMessageViewComponent(token)

    override fun layout(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)
            component(textMessageViewComponent) {
                lparams(matchParent, wrapContent)
            }
            background = ContextCompat.getDrawable(context, android.R.color.holo_red_dark)
        }
    }

    override fun on(state: State) {
        val bgColor = (state as? BackgroundColorProvider)
        bgColor?.let {
            textMessageViewComponent.rootLayout.backgroundColor = it.bgColor
        }
        textMessageViewComponent.on(state)
    }

    override fun on(item: ItemModel, position: Int) {
        val bgColor = (item as? BackgroundColorProvider)
        bgColor?.let {
            textMessageViewComponent.rootLayout.backgroundColor = it.bgColor
        }
        textMessageViewComponent.on(item, position)
    }
}