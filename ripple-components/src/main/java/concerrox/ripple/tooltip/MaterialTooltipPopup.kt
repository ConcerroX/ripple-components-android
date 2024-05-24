package concerrox.ripple.tooltip

import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import concerrox.ripple.internal.activity

class MaterialTooltipPopup(private val helper: MaterialTooltipHelper) {

    companion object {
        const val TAG = "MaterialTooltipPopup"
    }

    private val layoutParams = WindowManager.LayoutParams()
    private val isShowing: Boolean
        get() = helper.tooltip.view.parent != null
    private val displayBounds = Rect()

    fun show(anchor: View, anchorCoordinate: Point, fromTouch: Boolean) {
        if (isShowing) {
//            hide()
        }
        layoutParams.setTitle(javaClass.getSimpleName())
        layoutParams.packageName = anchor.context.packageName
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.windowAnimations = androidx.appcompat.R.style.Animation_AppCompat_Tooltip
        layoutParams.flags = (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        computePosition(anchor, anchorCoordinate, fromTouch, layoutParams)
        anchor.activity?.windowManager?.addView(helper.tooltip.view, layoutParams)
    }

    private fun computePosition(
        anchor: View, anchorCoordinate: Point, fromTouch: Boolean, outParams: WindowManager.LayoutParams
    ) {
        val context = anchor.context
        outParams.token = anchor.getApplicationWindowToken()
        val tooltipPreciseAnchorThreshold: Int = context.resources.getDimensionPixelOffset(
            androidx.appcompat.R.dimen.tooltip_precise_anchor_threshold
        )
        val offsetX: Int = if (anchor.width >= tooltipPreciseAnchorThreshold) {
            // Wide view. Align the tooltip horizontally to the precise X position.
            anchorCoordinate.x
        } else {
            // Otherwise anchor the tooltip to the view center.
            anchor.width / 2 // Center on the view horizontally.
        }
        val offsetBelow: Int
        val offsetAbove: Int
        if (anchor.height >= tooltipPreciseAnchorThreshold) {
            // Tall view. Align the tooltip vertically to the precise Y position.
            val offsetExtra: Int = context.resources.getDimensionPixelOffset(
                androidx.appcompat.R.dimen.tooltip_precise_anchor_extra_offset
            )
            offsetBelow = anchorCoordinate.y + offsetExtra
            offsetAbove = anchorCoordinate.y - offsetExtra
        } else {
            // Otherwise anchor the tooltip to the view center.
            offsetBelow = anchor.height // Place below the view in most cases.
            offsetAbove = 0 // Place above the view if the tooltip does not fit below.
        }
        outParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
        val tooltipOffset: Int = context.resources.getDimensionPixelOffset(
            if (fromTouch) androidx.appcompat.R.dimen.tooltip_y_offset_touch else androidx.appcompat.R.dimen.tooltip_y_offset_non_touch
        )
        val appRootView = getAppRootView(anchor)
        appRootView.getWindowVisibleDisplayFrame(displayBounds)
        if (displayBounds.left < 0 && displayBounds.top < 0) {
            // No meaningful display frame, the anchor view is probably in a sub-panel (such as a popup window).
            // Use the screen frame as a reasonable approximation.
            val res = context.resources
            val statusBarHeight: Int
            val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
            statusBarHeight = if (resourceId != 0) res.getDimensionPixelSize(resourceId) else 0
            val metrics = res.displayMetrics
            displayBounds.set(0, statusBarHeight, metrics.widthPixels, metrics.heightPixels)
        }

        val mTmpAnchorPos = IntArray(2)
        val mTmpAppPos = IntArray(2)

        appRootView.getLocationOnScreen(mTmpAppPos)
        anchor.getLocationOnScreen(mTmpAnchorPos)
        mTmpAnchorPos[0] -= mTmpAppPos[0]
        mTmpAnchorPos[1] -= mTmpAppPos[1]
        // mTmpAnchorPos is now relative to the main app window.
        outParams.x = mTmpAnchorPos[0] + offsetX - appRootView.width / 2
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        helper.tooltip.view.measure(spec, spec)
        val tooltipHeight: Int = helper.tooltip.view.measuredHeight
        val yAbove = mTmpAnchorPos[1] + offsetAbove - tooltipOffset - tooltipHeight
        val yBelow = mTmpAnchorPos[1] + offsetBelow + tooltipOffset
        if (fromTouch) {
            if (yAbove >= 0) {
                outParams.y = yAbove
            } else {
                outParams.y = yBelow
            }
        } else {
            if (yBelow + tooltipHeight <= displayBounds.height()) {
                outParams.y = yBelow
            } else {
                outParams.y = yAbove
            }
        }
    }

    private fun getAppRootView(anchor: View): View {
        val rootView = anchor.getRootView()
        val rootLayoutParams = rootView.layoutParams
        if (rootLayoutParams is WindowManager.LayoutParams && (rootLayoutParams.type == WindowManager.LayoutParams.TYPE_APPLICATION)) {
            // This covers regular app windows and Dialog windows.
            return rootView
        }
        // For non-application window types (such as popup windows) try to find the main app window
        // through the context.
        anchor.activity?.let {
            return it.window.decorView
        }
        // Main app window not found, fall back to the anchor's root view. There is no guarantee
        // that the tooltip position will be computed correctly.
        return rootView
    }


}