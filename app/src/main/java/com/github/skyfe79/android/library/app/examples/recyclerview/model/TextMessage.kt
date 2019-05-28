package com.github.skyfe79.android.library.app.examples.recyclerview.model

import com.github.skyfe79.android.library.app.examples.recyclerview.component.SectionViewComponent
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import kotlin.reflect.KClass

interface TextMessageProvider {
    val message: String
}

interface BackgroundColorProvider {
    val bgColor: Int
}

data class TextMessage(override val message: String, override val bgColor: Int = 0xFFBB0000.toInt(), override val isSticky: Boolean = false): ItemModel(), TextMessageProvider, BackgroundColorProvider {
    override val id: Int = hashCode()
    override val componentClass: KClass<*> = SectionViewComponent::class
}