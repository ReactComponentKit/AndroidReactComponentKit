package com.github.skyfe79.android.library.app.examples.emojicollection.util

object EmojiHelper {
    private val emojis = 0x1F600..0x1F64F
    val emoji: String
        get() = String(Character.toChars(emojis.random()))
}