package com.github.skyfe79.android.reactcomponentkit.collectionmodels

import kotlin.reflect.KClass

// TODO rename it!!! -> CellClassProvider
interface ViewComponentClassProvider {
    val componentClass: KClass<*>
}

interface ItemModel: ViewComponentClassProvider {
    val id: Int
}