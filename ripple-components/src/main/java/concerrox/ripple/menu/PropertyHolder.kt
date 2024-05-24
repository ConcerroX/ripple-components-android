package concerrox.ripple.menu

import android.graphics.Rect
import android.view.View
import concerrox.ripple.drawable.BoundedDrawable

class PropertyHolder(private val background: BoundedDrawable, val contentView: View) {

    var bounds: Rect?
        get() = background.bounds
        set(value) {
            background.drawableBounds = value!!
            contentView.invalidateOutline()
        }
}