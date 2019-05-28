package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.R
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.collectionview.SectionContent
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import kotlinx.android.synthetic.main.rck_nested_collection_view_component.view.*
import org.jetbrains.anko.AnkoContext
import kotlin.reflect.KClass


internal class RecyclerViewCell(private val token: Token, private val receiveState: Boolean = false) {

    lateinit var viewComponent: ViewComponent

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = viewComponent.createView(AnkoContext.create(parent.context))
        return ViewHolder(view)
    }

    fun onCreateCollectionViewHolder(parent: ViewGroup): CollectionViewHolder {
        val view = viewComponent.createView(AnkoContext.create(parent.context))
        return CollectionViewHolder(view)
    }

    private fun configure(itemModel: ItemModel, position: Int) {
        viewComponent.on(itemModel, position)
    }

    private fun configure(content: SectionContent, position: Int) {
        viewComponent.on(content, position)
    }

    inner class ViewHolder(cellView: View): RecyclerViewCellViewHolder(cellView) {
        override fun onBind(itemModel: ItemModel, position: Int) {
            configure(itemModel, position)
        }
    }

    inner class CollectionViewHolder(cellView: View): CollectionViewCellViewHolder(cellView) {
        override fun onBind(content: SectionContent, position: Int) {
            configure(content, position)
        }
    }
}