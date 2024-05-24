package concerrox.ripple.drawable

import android.graphics.Rect
import android.graphics.drawable.Drawable

/**
 * A wrapped [Drawable] that force use its own bounds to draw.
 * It maybe a little dirty. But if we don't do that, during the expanding animation, there will be
 * one or two frame using wrong bounds because of parent view sets bounds.
 */
class BoundedDrawable(wrappedDrawable: Drawable) : DrawableWrapper(wrappedDrawable) {

    private fun setDrawableBounds(left: Int, top: Int, right: Int, bottom: Int) {
        setBounds(left, top, right, bottom)
        wrappedDrawable.setBounds(left, top, right, bottom)
    }

    var drawableBounds: Rect = wrappedDrawable.bounds
        set(value) {
            field = value
            setDrawableBounds(value.left, value.top, value.right, value.bottom)
        }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {}
    override fun setBounds(bounds: Rect) {}
}