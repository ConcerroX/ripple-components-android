package concerrox.ripple.tooltip

import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.View.OnHoverListener
import android.view.View.OnLongClickListener
import android.view.ViewConfiguration
import androidx.core.view.ViewCompat

class MaterialTooltipHelper(internal val tooltip: MaterialTooltip) : OnLongClickListener, OnHoverListener,
    OnAttachStateChangeListener {

    companion object {
        private const val TAG = "MaterialTooltipHelper"
        private const val LONG_CLICK_HIDE_TIMEOUT_MILLISECONDS = 2500L
        private const val HOVER_HIDE_TIMEOUT_MILLISECONDS = 15000L
        private const val HOVER_HIDE_TIMEOUT_SHORT_MILLISECONDS = 3000L
    }

    private val popup = MaterialTooltipPopup(this)
    private var anchor: View? = null
    private var anchorCoordinate = Point(0, 0)
    private var text: CharSequence? = null

    internal fun attachToView(view: View) {
        anchor = view
        view.setOnLongClickListener(this)
        view.setOnHoverListener(this)
    }

    private fun show(fromTouch: Boolean) {
        val anchor = anchor
        if (anchor == null) {
            return
        } else if (!ViewCompat.isAttachedToWindow(anchor)) {
            return
        }
        popup.show(anchor, anchorCoordinate, fromTouch)
        // Only listen for attach state change while the popup is being shown.
        anchor.addOnAttachStateChangeListener(this)

        val timeout = if (fromTouch) {
            LONG_CLICK_HIDE_TIMEOUT_MILLISECONDS
        } else if ((ViewCompat.getWindowSystemUiVisibility(anchor) and View.SYSTEM_UI_FLAG_LOW_PROFILE) == View.SYSTEM_UI_FLAG_LOW_PROFILE) {
            HOVER_HIDE_TIMEOUT_SHORT_MILLISECONDS
        } else {
            HOVER_HIDE_TIMEOUT_MILLISECONDS - ViewConfiguration.getLongPressTimeout()
        }
        anchor.postDelayed(::hide, timeout)
    }

    private fun hide() {

    }

    override fun onLongClick(view: View?): Boolean {
        view?.let {
            anchorCoordinate.x = view.width / 2
            anchorCoordinate.y = view.height / 2
            show(true)
        }
        return true
    }

    override fun onHover(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onViewAttachedToWindow(v: View) {
        TODO("Not yet implemented")
    }

    override fun onViewDetachedFromWindow(v: View) {
        TODO("Not yet implemented")
    }


}