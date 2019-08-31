package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.view.View
import android.view.ViewGroup
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.collectionview.SectionContent
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import org.jetbrains.anko.AnkoContext


internal class RecyclerViewCell(private val token: Token) {

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
        override fun onBind(contents: SectionContent, position: Int) {
            configure(contents, position)
        }
    }
}