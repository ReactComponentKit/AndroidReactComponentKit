package com.github.skyfe79.android.reactcomponentkit.collectionmodels

interface SectionModel {
    var items: Array<ItemModel>

    var header: ItemModel?
    var footer: ItemModel?

    val itemCount: Int
        get() = items.size
}