package concerrox.ripple.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import concerrox.ripple.Material
import concerrox.ripple.internal.xInWindow
import concerrox.ripple.R
import concerrox.ripple.internal.ResourcesUtils
import kotlin.math.roundToInt

@SuppressLint("ResourceType")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class SimpleMenuPopupWindow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.styleable.SimpleMenuPreference_android_popupMenuStyle,
    defStyleRes: Int = R.style.Widget_Preference_SimpleMenuPreference_PopupMenu
) : PopupWindow(context, attrs, defStyleAttr, defStyleRes) {

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private val elevation: Float
    internal val horizontalMargin: Int
    internal val verticalMargin: Int
    internal val horizontalPadding: Int
    internal val verticalPadding: Int
    private val itemHeight: Int
    protected val increment: Int
    private val maxIncrements: Int
    var mode = POPUP_MENU
    private var requestMeasure = true
    private val recyclerView: RecyclerView
    private val adapter: SimpleMenuListAdapter = SimpleMenuListAdapter(this)
    var onItemClickListener: OnItemClickListener? = null
    var selectedIndex = 0
    var entries: List<CharSequence> = listOf()
        set(value) {
            field = value
            requestMeasure()
        }
    private var mMeasuredWidth = 0

    init {
        isFocusable = true
        isOutsideTouchable = false

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.SimpleMenuPopupWindow, defStyleAttr, defStyleRes
        )
        elevation = typedArray.getDimension(R.styleable.SimpleMenuPopupWindow_menuElevation, 4f)
        horizontalMargin =
            typedArray.getDimension(R.styleable.SimpleMenuPopupWindow_listMarginHorizontal, 0f)
                .toInt()
        verticalMargin =
            typedArray.getDimension(R.styleable.SimpleMenuPopupWindow_listMarginVertical, 0f)
                .toInt()
        horizontalPadding =
            typedArray.getDimension(R.styleable.SimpleMenuPopupWindow_listItemPadding, 0f).toInt()
        increment = typedArray.getDimension(R.styleable.SimpleMenuPopupWindow_increment, 0f).toInt()
        maxIncrements = typedArray.getInteger(R.styleable.SimpleMenuPopupWindow_maxIncrements, 0)
        typedArray.recycle()

        contentView = (LayoutInflater.from(context)
            .inflate(R.layout.simple_menu_list, null, false) as RecyclerView).apply {
            isFocusable = true
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    this@SimpleMenuPopupWindow.background.getOutline(outline)
                }
            }
            clipToOutline = true
            adapter = this@SimpleMenuPopupWindow.adapter
        }
        recyclerView = contentView

        if (Material.isMaterial3Enabled(context)) {
            setBackgroundDrawable(CustomBoundsDrawable(
                MaterialShapeDrawable(
                    ResourcesUtils.getShapeAppearanceModelFromAttribute(
                        context, com.google.android.material.R.attr.shapeAppearanceCornerSmall
                    )
                ).apply {
                    fillColor = ColorStateList.valueOf(
                        MaterialColors.getColorOrNull(
                            context,
                            com.google.android.material.R.attr.colorSurfaceContainer
                        )!!
                    )
                }
            ))
        } else {
            setBackgroundDrawable(CustomBoundsDrawable(
                MaterialShapeDrawable(
                    ResourcesUtils.getShapeAppearanceModelFromAttribute(
                        context, com.google.android.material.R.attr.shapeAppearanceSmallComponent
                    )
                ).apply {
                    fillColor = ColorStateList.valueOf(
                        MaterialColors.getColorOrNull(
                            context,
                            com.google.android.material.R.attr.colorSurface
                        )!!
                    )
                }
            ))
        }

        // TODO do not hardcode
        itemHeight = (context.resources.displayMetrics.density * 48).roundToInt()
        verticalPadding = (context.resources.displayMetrics.density * 8).roundToInt()
    }

    override fun getContentView(): RecyclerView {
        return super.getContentView() as RecyclerView
    }

    override fun getBackground(): CustomBoundsDrawable {
        val background = super.getBackground()
        if (background != null && background !is CustomBoundsDrawable) {
            setBackgroundDrawable(background)
        }
        return super.getBackground() as CustomBoundsDrawable
    }

    override fun setBackgroundDrawable(background: Drawable) {
        var boundedBackground = background
        if (boundedBackground !is CustomBoundsDrawable) {
            boundedBackground = CustomBoundsDrawable(boundedBackground)
        }
        super.setBackgroundDrawable(boundedBackground)
    }

    /**
     * Show the PopupWindow
     *
     * @param anchor      View that will be used to calc the position of windows
     * @param container   View that will be used to calc the position of windows
     * @param extraMargin extra margin start
     */
    @SuppressLint("NotifyDataSetChanged")
    fun show(anchor: View, container: View, extraMargin: Int) {
        val maxMaxWidth = container.width - horizontalMargin * 2
        val measuredWidth = measureWidth(maxMaxWidth, entries)
        if (measuredWidth == -1) {
            mode = POPUP_MENU
            mMeasuredWidth = measuredWidth
        } else if (measuredWidth != 0) {
            mode = POPUP_MENU
            mMeasuredWidth = measuredWidth
        }
        adapter.notifyDataSetChanged()

        // clear last bounds
        val zeroRect = Rect()
        background.setCustomBounds(zeroRect)
        contentView.invalidateOutline()
        showPopupMenu(anchor, container, mMeasuredWidth, extraMargin)
//            if (SimpleMenuPreference.isLightFixEnabled()) {
//                mList.post {
//                    Light.resetLightCenterForPopupWindow(
//                        this@SimpleMenuPopupWindow
//                    )
//                }
//            }
    }

    /**
     * Show popup window in popup mode
     *
     * @param anchor    View that will be used to calc the position of the window
     * @param container Container view that holds preference list, also used to calc width
     * @param width     Measured width of this window
     */
    private fun showPopupMenu(anchor: View, container: View, width: Int, extraMargin: Int) {
        val rtl = container.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
        val index = 0.coerceAtLeast(selectedIndex)
        val count = entries.size
        val anchorTop = anchor.top - container.paddingTop
        val anchorHeight = anchor.height
        val measuredHeight = itemHeight * count + verticalPadding * 2
        val location = IntArray(2)
        container.getLocationInWindow(location)
        val containerTopInWindow = location[1] + container.paddingTop
        val containerHeight = container.height - container.paddingTop - container.paddingBottom
        var y: Int
        val height: Int
        val elevation = elevation
        val centerX = if (rtl) location[0] + extraMargin - width else location[0] + extraMargin
        val centerY: Int
        val animItemHeight = itemHeight + verticalPadding * 2
        val animStartRect: Rect
        if (measuredHeight > containerHeight) {
            // too high, use scroll
            y = containerTopInWindow + verticalMargin

            // scroll to select item
            val scroll = (itemHeight * index - anchorTop) + verticalPadding + verticalMargin
            -anchorHeight / 2 + itemHeight / 2
            contentView.post {
                contentView.scrollBy(0, -measuredHeight) // to top
                contentView.scrollBy(0, scroll)
            }
            contentView.overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS
            height = containerHeight - verticalMargin * 2
            centerY = itemHeight * index
        } else {
            // calc align to selected
            y =
                containerTopInWindow + anchorTop + anchorHeight / 2 - itemHeight / 2 - verticalPadding - index * itemHeight

            // make sure window is in parent view
            val maxY = containerTopInWindow + containerHeight - measuredHeight - verticalMargin
            y = y.coerceAtMost(maxY)
            val minY = containerTopInWindow + verticalMargin
            y = y.coerceAtLeast(minY)
            contentView.overScrollMode = View.OVER_SCROLL_NEVER
            height = measuredHeight

            // center of selected item
            centerY = (verticalPadding + index * itemHeight + itemHeight * 0.5).toInt()
        }
        setWidth(width)
        setHeight(height)
        setElevation(elevation)
        animationStyle = R.style.Animation_Preference_SimpleMenuCenter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            enterTransition = null
            exitTransition = null
        }
        super.showAtLocation(anchor, Gravity.NO_GRAVITY, centerX + anchor.xInWindow, y)
        val startTop = centerY - (itemHeight * 0.2).toInt()
        val startBottom = centerY + (itemHeight * 0.2).toInt()
        val startLeft: Int
        val startRight: Int
        if (!rtl) {
            startLeft = centerX
            startRight = centerX + increment
        } else {
            startLeft = centerX + width - increment
            startRight = centerX + width
        }
        animStartRect = Rect(startLeft, startTop, startRight, startBottom)
        val animElevation = (elevation * 0.25).roundToInt()
        contentView.post {
            SimpleMenuAnimation.startEnterAnimation(
                background,
                contentView,
                width,
                height,
                centerX,
                centerY,
                animStartRect,
                animItemHeight,
                animElevation,
                index
            )
        }
    }

    /**
     * Request a measurement before next show, call this when entries changed.
     */
    fun requestMeasure() {
        requestMeasure = true
    }

    /**
     * Measure window width
     *
     * @param maxWidth max width for popup
     * @param entries  Entries of preference hold this window
     * @return 0: skip
     * -1: use dialog
     * other: measuredWidth
     */
    @SuppressLint("InflateParams")
    private fun measureWidth(maxWidth: Int, entries: List<CharSequence>): Int {
        var newMaxWidth = maxWidth
        if (!requestMeasure) {
            return 0
        } else {
            requestMeasure = false
        }
        val sortedEntries = entries.sortedWith { a: CharSequence, b: CharSequence ->
            b.length - a.length
        }
        var width = 0
        newMaxWidth = (increment * maxIncrements).coerceAtMost(newMaxWidth)
        val bounds = Rect()
        val view =
            LayoutInflater.from(contentView.context).inflate(R.layout.simple_menu_item, null, false)
                .findViewById<TextView>(android.R.id.text1)
        val textPaint: Paint = view.paint
        for (chs in sortedEntries) {
            textPaint.getTextBounds(chs.toString(), 0, chs.toString().length, bounds)
            width =
                width.coerceAtLeast(bounds.right + 1 + Math.round((horizontalPadding * 2 + 1).toFloat()))

            // more than one line should use dialog
            if (width > newMaxWidth || chs.toString().contains("\n")) {
                return -1
            }
        }

        // width is a multiple of a unit
        var w = 0
        while (width > w) {
            w += increment
        }
        return w
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        throw UnsupportedOperationException("use show(anchor) to show the window")
    }

    override fun showAsDropDown(anchor: View) {
        throw UnsupportedOperationException("use show(anchor) to show the window")
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        throw UnsupportedOperationException("use show(anchor) to show the window")
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        throw UnsupportedOperationException("use show(anchor) to show the window")
    }

    companion object {
        const val POPUP_MENU = 0
        const val DIALOG = 1
    }
}