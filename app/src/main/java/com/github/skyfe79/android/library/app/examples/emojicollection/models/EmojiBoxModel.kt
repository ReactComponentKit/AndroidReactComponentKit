package com.github.skyfe79.android.library.app.examples.emojicollection.models

import com.github.skyfe79.android.library.app.examples.emojicollection.components.EmojiViewComponent
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import kotlin.reflect.KClass

interface EmojiProvider {
    val emoji: String
}

data class EmojiBoxModel(override val emoji: String): ItemModel(), EmojiProvider {
    override val id: Int
        get() = emoji.hashCode()
    override val componentClass: KClass<*>
        get() = EmojiViewComponent::class
}