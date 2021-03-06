package com.github.skyfe79.android.library.app.examples.emojicollection.components

import android.content.Context
import android.view.Gravity.CENTER
import android.view.View
import android.widget.TextView
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiProvider
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.dispatcher.dispatch
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.redux.Action
import org.jetbrains.anko.*

data class ClickEmojiAction(val emoji: String): Action

class EmojiViewComponent(token: Token): ViewComponent(token) {
    private lateinit var emojiTextView: TextView

    override fun layout(ui: AnkoContext<Context>): View = with(ui) {
        val rootLayout = relativeLayout {
            lparams(matchParent, dip(40))
            emojiTextView = textView {
                gravity = CENTER
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textSize = 20f
            }.lparams {
                centerInParent()
            }
        }

        rootLayout.setOnClickListener {
            this@EmojiViewComponent.token
            dispatch(ClickEmojiAction(emojiTextView.text.toString()))
        }

        return rootLayout
    }

    override fun on(item: ItemModel, position: Int) {
        val emojiProvider = (item as? EmojiProvider) ?: return
        emojiTextView.text = emojiProvider.emoji
    }
}