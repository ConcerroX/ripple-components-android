package concerrox.ripple.internal

import android.view.View

val View.xInWindow: Int
    get() {
        val location = IntArray(2)
        getLocationInWindow(location)
        return location[0]
    }

val View.yInWindow: Int
    get() {
        val location = IntArray(2)
        getLocationInWindow(location)
        return location[1]
    }

val View.leftToRight: Int
    get() {
        return right - left
    }

val View.topToBottom: Int
    get() {
        return bottom - top
    }

val View.centerX: Int
    get() {
        return left + leftToRight / 2
    }

val View.centerY: Int
    get() {
        return top + topToBottom / 2
    }