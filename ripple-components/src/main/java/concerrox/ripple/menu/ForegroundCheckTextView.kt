package concerrox.ripple.menu

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.RestrictTo
import androidx.appcompat.widget.AppCompatCheckedTextView

/**
 * Extension of CheckedTextView that adds a Foreground drawable.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class ForegroundCheckTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatCheckedTextView(context, attrs, defStyleAttr) {
    private var mForeground: Drawable? = null
    private val mSelfBounds = Rect()
    private val mOverlayBounds = Rect()
    private var mForegroundGravity = Gravity.FILL
    private var mForegroundInPadding = true
    private var mForegroundBoundsChanged = false

    init {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
//        val a = context.obtainStyledAttributes(
//            attrs, R.styleable.ForegroundCheckTextView,
//            defStyleAttr, 0
//        )
//        mForegroundGravity = a.getInt(
//            R.styleable.ForegroundCheckTextView_android_foregroundGravity, mForegroundGravity
//        )
//        val d = a.getDrawable(R.styleable.ForegroundCheckTextView_android_foreground)
//        if (d != null) {
//            foreground = d
//        }
//        mForegroundInPadding = a.getBoolean(
//            R.styleable.ForegroundCheckTextView_foregroundInsidePadding, true
//        )
//        a.recycle()
    }

    /**
     * Describes how the foreground is positioned.
     *
     * @return foreground gravity.
     * @see .setForegroundGravity
     */
    override fun getForegroundGravity(): Int {
        return mForegroundGravity
    }

    /**
     * Describes how the foreground is positioned. Defaults to START and TOP.
     *
     * @param foregroundGravity See [Gravity]
     * @see .getForegroundGravity
     */
    override fun setForegroundGravity(foregroundGravity: Int) {
        var gravity = foregroundGravity
        if (mForegroundGravity != gravity) {
            if (gravity and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK == 0) {
                gravity = gravity or Gravity.START
            }
            if (gravity and Gravity.VERTICAL_GRAVITY_MASK == 0) {
                gravity = gravity or Gravity.TOP
            }
            mForegroundGravity = gravity
            if (mForegroundGravity == Gravity.FILL && mForeground != null) {
                val padding = Rect()
                mForeground!!.getPadding(padding)
            }
            requestLayout()
        }
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === mForeground
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        if (mForeground != null) {
            mForeground!!.jumpToCurrentState()
        }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (mForeground != null && mForeground!!.isStateful) {
            mForeground!!.state = drawableState
        }
    }

    /**
     * Supply a Drawable that is to be rendered on top of all of the child
     * views in the frame layout.  Any padding in the Drawable will be taken
     * into account by ensuring that the children are inset to be placed
     * inside of the padding area.
     *
     * @param drawable The Drawable to be drawn on top of the children.
     */
    override fun setForeground(drawable: Drawable) {
        if (mForeground !== drawable) {
            if (mForeground != null) {
                mForeground!!.callback = null
                unscheduleDrawable(mForeground)
            }
            mForeground = drawable
            setWillNotDraw(false)
            drawable.callback = this
            if (drawable.isStateful) {
                drawable.state = drawableState
            }
            if (mForegroundGravity == Gravity.FILL) {
                val padding = Rect()
                drawable.getPadding(padding)
            }
            requestLayout()
            invalidate()
        }
    }

    /**
     * Returns the drawable used as the foreground of this FrameLayout. The
     * foreground drawable, if non-null, is always drawn on top of the children.
     *
     * @return A Drawable or null if no foreground was set.
     */
    override fun getForeground(): Drawable {
        return mForeground!!
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mForegroundBoundsChanged = mForegroundBoundsChanged or changed
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mForegroundBoundsChanged = true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (mForeground != null) {
            val foreground: Drawable = mForeground as Drawable
            if (mForegroundBoundsChanged) {
                mForegroundBoundsChanged = false
                val selfBounds = mSelfBounds
                val overlayBounds = mOverlayBounds
                val w = right - left
                val h = bottom - top
                if (mForegroundInPadding) {
                    selfBounds[0, 0, w] = h
                } else {
                    selfBounds[paddingLeft, paddingTop, w - paddingRight] = h - paddingBottom
                }
                Gravity.apply(
                    mForegroundGravity, foreground.intrinsicWidth,
                    foreground.intrinsicHeight, selfBounds, overlayBounds
                )
                foreground.bounds = overlayBounds
            }
            foreground.draw(canvas)
        }
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        if (mForeground != null) {
            mForeground!!.setHotspot(x, y)
        }
    }
}