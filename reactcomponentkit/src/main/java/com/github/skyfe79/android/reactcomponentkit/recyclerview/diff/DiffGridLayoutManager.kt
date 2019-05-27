package com.github.skyfe79.android.reactcomponentkit.recyclerview.diff

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager

/**
 * Fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder Exception
 * This exception(or crash) is occured when suffle the existing items.
 */
open class DiffGridLayoutManager: GridLayoutManager {
    constructor(context: Context?, spanCount: Int) : super(
        context,
        spanCount
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    constructor(context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(
        context,
        spanCount,
        orientation,
        reverseLayout
    )

    /**
     * Fix suffle issue when using diffutil
     */
    override fun supportsPredictiveItemAnimations(): Boolean = false
}