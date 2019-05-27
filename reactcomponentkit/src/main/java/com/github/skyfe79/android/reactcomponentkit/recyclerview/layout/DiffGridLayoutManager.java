package com.github.skyfe79.android.reactcomponentkit.recyclerview.layout;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder Exception
 * This exception(or crash) is occured when suffle the existing items.
 */
public class DiffGridLayoutManager extends GridLayoutManager {

    public DiffGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DiffGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public DiffGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    /**
     * Fix suffle issue when using diffutil
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
