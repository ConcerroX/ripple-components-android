package concerrox.ripple.foreground

import android.graphics.drawable.Drawable
import android.view.View

var View.foregroundCompat: Drawable?
    set(value) {
        ForegroundCompat.setForeground(this, value)
    }
    get() {
        return ForegroundCompat.getForeground(this)
    }