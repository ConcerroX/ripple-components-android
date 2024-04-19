package concerrox.ripple.internal

import android.view.View

val View.xInWindow: Int
    get() {
        val location = IntArray(2)
        this.getLocationInWindow(location)
        return location[0]
    }

val View.yInWindow: Int
    get() {
        val location = IntArray(2)
        this.getLocationInWindow(location)
        return location[1]
    }