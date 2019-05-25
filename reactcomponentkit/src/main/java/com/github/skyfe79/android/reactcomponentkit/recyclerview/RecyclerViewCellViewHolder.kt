package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel

internal abstract class RecyclerViewCellViewHolder(cellView: View): RecyclerView.ViewHolder(cellView) {
    abstract fun onBind(itemModel: ItemModel, position: Int)
}
