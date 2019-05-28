package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.collectionview.SectionContent
import kotlin.reflect.KClass

internal abstract class RecyclerViewCellViewHolder(cellView: View): RecyclerView.ViewHolder(cellView) {
    abstract fun onBind(itemModel: ItemModel, position: Int)
}

internal abstract class CollectionViewCellViewHolder(cellView: View): RecyclerView.ViewHolder(cellView) {
    abstract fun onBind(contents: SectionContent, position: Int)
}