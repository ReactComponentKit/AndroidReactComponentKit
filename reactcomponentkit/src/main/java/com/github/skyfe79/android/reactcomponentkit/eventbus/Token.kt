package com.github.skyfe79.android.reactcomponentkit.eventbus

import java.util.*

data class Token(val token: String = UUID.randomUUID().toString()) {
    companion object {
        val empty: Token =
            Token("")
    }
}