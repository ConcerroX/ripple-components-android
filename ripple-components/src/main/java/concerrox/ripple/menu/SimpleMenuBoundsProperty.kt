package concerrox.ripple.menu

import android.graphics.Rect
import android.os.Build
import android.util.Property

class SimpleMenuBoundsProperty(name: String): Property<PropertyHolder, Rect>(Rect::class.java, name) {

    companion object {
        var BOUNDS = SimpleMenuBoundsProperty("bounds")
    }

    override fun get(holder: PropertyHolder): Rect? {
        return holder.bounds
    }

    override fun set(holder: PropertyHolder, value: Rect) {
        holder.bounds = value

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            holder.contentView.invalidate()
        }
    }
}