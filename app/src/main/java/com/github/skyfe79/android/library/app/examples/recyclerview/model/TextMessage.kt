package com.github.skyfe79.android.library.app.examples.recyclerview.model

import com.github.skyfe79.android.library.app.examples.recyclerview.component.ProfileViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.component.TextMessageViewComponent
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import kotlin.reflect.KClass

interface TextMessageProvider {
    val message: String
}

data class TextMessage(override val message: String): ItemModel, TextMessageProvider {
    override val id: Int = hashCode()
    override val componentClass: KClass<*> = ProfileViewComponent::class
}