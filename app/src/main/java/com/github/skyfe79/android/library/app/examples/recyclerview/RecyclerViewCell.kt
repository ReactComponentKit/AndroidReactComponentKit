package com.github.skyfe79.android.library.app.examples.recyclerview

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.State
import org.jetbrains.anko.*
import kotlin.reflect.KClass

abstract class RecyclerViewCell<T: ItemModel> {

    abstract val viewComponent: ViewComponent
    protected var token: Token = Token.empty
    protected var receiveState: Boolean = false

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = viewComponent.createView(AnkoContext.create(parent.context, parent))
        return ViewHolder(view)
    }

    fun set(token: Token, receiveState: Boolean) {
        this.token = token
        this.receiveState = receiveState
    }

    private fun configure(itemModel: ItemModel, position: Int) {
        viewComponent.on(itemModel, position)
    }

    inner class ViewHolder(cellView: View): CellViewHolder(cellView) {
        override fun onBind(itemModel: ItemModel, position: Int) {
            configure(itemModel, position)
        }
    }
}

data class TextMessage(val message: String): ItemModel {
    override val id: Int = hashCode()
    override val componentClass: KClass<*> = TextMessageCell::class
}

abstract class CellViewHolder(cellView: View): RecyclerView.ViewHolder(cellView) {
    abstract fun onBind(itemModel: ItemModel, position: Int)
}

class TextMessageViewComponent(override var token: Token, override var receiveState: Boolean): ViewComponent(token, receiveState) {
    lateinit var textView: TextView

    override fun layout(ui: AnkoContext<ViewGroup>): View = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)

            textView = textView {
                gravity = Gravity.CENTER
            }.lparams(matchParent, dip(50))
        }
    }

    override fun on(state: State) {

    }

    override fun on(item: ItemModel, position: Int) {
        val item = (item as? TextMessage) ?: return
        textView.text = item.message
    }
}

class TextMessageCell: RecyclerViewCell<TextMessage>() {
    override val viewComponent = TextMessageViewComponent(token, receiveState)
}