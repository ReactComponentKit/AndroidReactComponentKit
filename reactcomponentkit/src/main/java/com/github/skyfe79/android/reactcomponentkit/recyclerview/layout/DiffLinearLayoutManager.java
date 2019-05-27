package com.github.skyfe79.android.reactcomponentkit.recyclerview.layout;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder Exception
 * This exception(or crash) is occured when suffle the existing items.
 */
public class DiffLinearLayoutManager extends LinearLayoutManager {
    public DiffLinearLayoutManager(Context context) {
        super(context);
    }

    public DiffLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public DiffLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Fix suffle issue when using diffutil
     */
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
