package com.github.skyfe79.android.reactcomponentkit.collectionmodels

import kotlin.reflect.KClass

interface ViewComponentClassProvider {
    val componentClass: KClass<*>
}

abstract class ItemModel: ViewComponentClassProvider {
    abstract val id: Int
    open val isSticky: Boolean = false
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemModel

        if (id != other.id) return false
        if (isSticky != other.isSticky) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + isSticky.hashCode()
        return result
    }
}

