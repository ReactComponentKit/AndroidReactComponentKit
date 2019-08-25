package com.github.skyfe79.android.reactcomponentkit.redux

// Utility for something like as reset some state.
// Run it after dispatching new state to Components
typealias After<STATE> = (STATE) -> STATE