package com.github.skyfe79.android.reactcomponentkit.recyclerview.diff

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder Exception
 * This exception(or crash) is occured when suffle the existing items.
 */
open class DiffStaggeredGridLayoutManager: StaggeredGridLayoutManager {
    constructor(spanCount: Int, orientation: Int) : super(spanCount, orientation)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    /**
     * Fix suffle issue when using diffutil
     */
    override fun supportsPredictiveItemAnimations(): Boolean = false
}