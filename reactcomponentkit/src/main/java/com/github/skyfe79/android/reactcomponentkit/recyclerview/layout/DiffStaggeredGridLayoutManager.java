package com.github.skyfe79.android.reactcomponentkit.recyclerview.layout;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder Exception
 * This exception(or crash) is occured when suffle the existing items.
 */
public class DiffStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    public DiffStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DiffStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    /**
     * Fix suffle issue when using diffutil
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
