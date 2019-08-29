package com.github.skyfe79.android.reactcomponentkit.util

import android.os.Build
import android.view.View
import java.util.concurrent.atomic.AtomicInteger

class ViewId {
    companion object {
        private val nextId = AtomicInteger(1)
        val gen: Int
            get() {
                if (Build.VERSION.SDK_INT > 17) {
                    return View.generateViewId()
                } else {
                    while (true) {
                        val result = nextId.get()
                        var newValue = result + 1
                        if (newValue > 0x00FFFFFF) newValue = 1
                        if (nextId.compareAndSet(result, newValue)) {
                            return result
                        }
                    }
                }
            }
        }
}
