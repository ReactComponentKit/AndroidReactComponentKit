package com.github.skyfe79.android.reactcomponentkit.recyclerview.layout

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder Exception
 * This exception(or crash) is occured when suffle the existing items.
 */
open class DiffLinearLayoutManager : LinearLayoutManager {
    private val defaultExtraLayoutSpace = 600
    private var extraLayoutSpace = -1
    private var context: Context? = null

    constructor(context: Context) : super(context) {
        this.context = context
    }

    constructor(context: Context, extraLayoutSpace: Int) : super(context) {
        this.context = context
        this.extraLayoutSpace = extraLayoutSpace
    }

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )
    /**
     * Fix suffle issue when using diffutil
     */
    override fun supportsPredictiveItemAnimations(): Boolean = false

    fun setExtraLayoutSpace(extraLayoutSpace: Int) {
        this.extraLayoutSpace = extraLayoutSpace
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return if (extraLayoutSpace > 0) {
            extraLayoutSpace
        } else defaultExtraLayoutSpace
    }
}
