package concerrox.ripple.menu

import android.graphics.Rect
import android.view.View

class PropertyHolder(private val background: CustomBoundsDrawable, val contentView: View) {

    var bounds: Rect?
        get() = background.bounds
        set(value) {
            background.setCustomBounds(value!!)
            contentView.invalidateOutline()
        }
}