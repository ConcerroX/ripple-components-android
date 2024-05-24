package concerrox.ripple.drawable

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat

open class DrawableWrapper(var wrappedDrawable: Drawable) :
    Drawable(), Drawable.Callback {
    private var mDrawable: Drawable = wrappedDrawable

    init {
        wrappedDrawable.callback = this
    }

    override fun getOutline(outline: Outline) {
        mDrawable.getOutline(outline)
    }

    override fun draw(canvas: Canvas) {
        mDrawable.draw(canvas)
    }

    override fun onBoundsChange(bounds: Rect) {
        mDrawable.bounds = bounds
    }

    override fun setChangingConfigurations(configs: Int) {
        mDrawable.changingConfigurations = configs
    }

    override fun getChangingConfigurations(): Int {
        return mDrawable.changingConfigurations
    }

    @Deprecated("Deprecated in Java")
    override fun setDither(dither: Boolean) {
        mDrawable.setDither(dither)
    }

    override fun setFilterBitmap(filter: Boolean) {
        mDrawable.isFilterBitmap = filter
    }

    override fun setAlpha(alpha: Int) {
        mDrawable.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mDrawable.colorFilter = cf
    }

    override fun isStateful(): Boolean {
        return mDrawable.isStateful
    }

    override fun setState(stateSet: IntArray): Boolean {
        return mDrawable.setState(stateSet)
    }

    override fun getState(): IntArray {
        return mDrawable.state
    }

    override fun jumpToCurrentState() {
        mDrawable.jumpToCurrentState()
    }

    override fun getCurrent(): Drawable {
        return mDrawable.current
    }

    override fun setVisible(visible: Boolean, restart: Boolean): Boolean {
        return super.setVisible(visible, restart) || mDrawable.setVisible(visible, restart)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("PixelFormat.OPAQUE", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int {
        return mDrawable.opacity
//        return PixelFormat.OPAQUE
    }

    override fun getTransparentRegion(): Region? {
        return mDrawable.transparentRegion
    }

    override fun getIntrinsicWidth(): Int {
        return mDrawable.intrinsicWidth
    }

    override fun getIntrinsicHeight(): Int {
        return mDrawable.intrinsicHeight
    }

    override fun getMinimumWidth(): Int {
        return mDrawable.minimumWidth
    }

    override fun getMinimumHeight(): Int {
        return mDrawable.minimumHeight
    }

    override fun getPadding(padding: Rect): Boolean {
        return mDrawable.getPadding(padding)
    }

    /**
     * {@inheritDoc}
     */
    override fun invalidateDrawable(who: Drawable) {
        invalidateSelf()
    }

    /**
     * {@inheritDoc}
     */
    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        scheduleSelf(what, `when`)
    }

    /**
     * {@inheritDoc}
     */
    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        unscheduleSelf(what)
    }

    override fun onLevelChange(level: Int): Boolean {
        return mDrawable.setLevel(level)
    }

    override fun setAutoMirrored(mirrored: Boolean) {
        DrawableCompat.setAutoMirrored(mDrawable, mirrored)
    }

    override fun isAutoMirrored(): Boolean {
        return DrawableCompat.isAutoMirrored(mDrawable)
    }

    override fun setTint(tint: Int) {
        DrawableCompat.setTint(mDrawable, tint)
    }

    override fun setTintList(tint: ColorStateList?) {
        DrawableCompat.setTintList(mDrawable, tint)
    }

    override fun setTintMode(tintMode: PorterDuff.Mode?) {
        DrawableCompat.setTintMode(mDrawable, tintMode)
    }

    override fun setHotspot(x: Float, y: Float) {
        DrawableCompat.setHotspot(mDrawable, x, y)
    }

    override fun setHotspotBounds(left: Int, top: Int, right: Int, bottom: Int) {
        DrawableCompat.setHotspotBounds(mDrawable, left, top, right, bottom)
    }

}