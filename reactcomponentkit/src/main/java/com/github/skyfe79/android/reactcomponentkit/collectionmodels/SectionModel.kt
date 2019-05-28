package com.github.skyfe79.android.reactcomponentkit.collectionmodels

import androidx.recyclerview.widget.RecyclerView

typealias RecyclerViewHelper = (recyclerView: RecyclerView) -> Unit

interface SectionModel {
    val items: List<ItemModel>
    val header: ItemModel?
    val footer: ItemModel?
    val recyclerViewHelper: RecyclerViewHelper
}



data class DefaultSectionModel(
    override val items: List<ItemModel>,
    override val header: ItemModel? = null,
    override val footer: ItemModel? = null,
    override val recyclerViewHelper: RecyclerViewHelper
): SectionModel