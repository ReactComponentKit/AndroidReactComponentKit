package com.github.skyfe79.android.library.app.examples.emojicollection.actions

import com.github.skyfe79.android.reactcomponentkit.redux.Action

data class AddEmojiAction(val emoji: String): Action
object RemoveEmojiAction: Action
object ShuffleEmojiAction: Action