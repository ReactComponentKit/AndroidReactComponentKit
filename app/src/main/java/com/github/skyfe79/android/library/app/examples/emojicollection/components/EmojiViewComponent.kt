package com.github.skyfe79.android.library.app.examples.emojicollection.components

import android.content.Context
import android.view.Gravity.CENTER
import android.view.View
import android.widget.TextView
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiProvider
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.*



class EmojiViewComponent(override var token: Token, override var receiveState: Boolean): ViewComponent(token, receiveState) {
    private lateinit var emojiTextView: TextView

    override fun layout(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(dip(40), dip(40))
            emojiTextView = textView {
                gravity = CENTER
                textSize = 20f
            }.lparams {
                centerInParent()
            }
        }
    }

    override fun on(state: State) {
        //ignore
    }

    override fun on(item: ItemModel, position: Int) {
        val emojiProvider = (item as? EmojiProvider) ?: return
        emojiTextView.text = emojiProvider.emoji
    }
}