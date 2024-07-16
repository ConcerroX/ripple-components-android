package concerrox.ui.ripple.list.item

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.Px
import androidx.core.view.forEach
import com.google.android.material.color.MaterialColors
import com.google.android.material.textview.MaterialTextView
import concerrox.ripple.R
import concerrox.ripple.foreground.foregroundCompat
import concerrox.ripple.internal.setTextAppearanceResource
import concerrox.ripple.ripple.RippleUtils
import concerrox.ui.foreground.ForegroundViewGroup
import concerrox.ui.ripple.list.ListItemGroup
import concerrox.ui.view.StyledAttributes
import concerrox.ui.view.dp2px

open class ListItem @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.listItemStyle,
    defStyleRes: Int = R.style.Widget_Ripple_Material2_ListItem,
) : ForegroundViewGroup(context, attrs) {

    enum class LineMode {
        ONE_LINE, TWO_LINE, TWO_LINE_WITH_OVERLINE, THREE_LINE, THREE_LINE_WITH_OVERLINE
    }

    enum class Slot(value: Int) {
        NO_SLOT(0), CENTER(1), START(2), END(3), END_DIVIDED(4), TOP(5), BOTTOM(6);

        companion object {
            fun parse(string: String) = entries.firstOrNull { it.name.equals(string, true) }
        }
    }

    class LayoutParams(width: Int, height: Int) : MarginLayoutParams(width, height) {
        constructor(source: ViewGroup.LayoutParams) : this(source.width, source.height)
        constructor(context: Context, attrs: AttributeSet?) : this(
            MarginLayoutParams(context, attrs)
        )

        var slot = Slot.NO_SLOT
        var isCenterAligned = false
    }

    companion object {
        const val TAG = "ListItem"
    }

    private var onClickListener: OnClickListener? = null

    private val primaryTextView = MaterialTextView(context).apply { maxLines = 1 }
    private val secondaryTextView = MaterialTextView(context).apply { maxLines = 2 }
    private val overlineTextView = MaterialTextView(context).apply { maxLines = 1 }
    private val metaTextView = MaterialTextView(context).apply { maxLines = 1 }

    private val centerChildViews = arrayListOf<View>()
    private val startChildViews = arrayListOf<View>()
    private val endChildViews = arrayListOf<View>()
    private val endDividedChildViews = arrayListOf<View>()
    private val topChildViews = arrayListOf<View>()
    private val bottomChildViews = arrayListOf<View>()

    private val hasStartOrEndChildViews
        get() = startChildViews.isNotEmpty() || endChildViews.isNotEmpty() || endDividedChildViews.isNotEmpty()

    private val helper = ListItemHelper(this, attrs, defStyleAttr, defStyleRes)

    var forceKeyline
        get() = helper.forceKeyline
        set(value) {
            helper.forceKeyline = value
            requestLayout()
        }
    var keyline
        get() = helper.keyline
        set(value) {
            helper.keyline = value
            requestLayout()
        }

    var title
        get() = helper.title
        set(value) {
            helper.title = value
            requestLayout()
        }
    var text
        get() = helper.text
        set(value) {
            helper.text = value
            requestLayout()
        }
    var meta
        get() = helper.meta
        set(value) {
            helper.meta = value
            requestLayout()
        }
    var overline
        get() = helper.overline
        set(value) {
            helper.overline = value
            requestLayout()
        }

    var lineMode
        get() = helper.lineMode
        set(value) {
            helper.lineMode = value
            requestLayout()
        }

    var twoLineOverlineTextBaselineToTopHeight
        get() = helper.twoLineOverlineTextBaselineToTopHeight
        set(value) {
            helper.twoLineOverlineTextBaselineToTopHeight = value
            requestLayout()
        }
    var twoLinePrimaryTextBaselineToTopHeight
        get() = helper.twoLinePrimaryTextBaselineToTopHeight
        set(value) {
            helper.twoLinePrimaryTextBaselineToTopHeight = value
            requestLayout()
        }
    var twoLinePrimaryTextBaselineToOverlineTextBaselineHeight
        get() = helper.twoLinePrimaryTextBaselineToOverlineTextBaselineHeight
        set(value) {
            helper.twoLinePrimaryTextBaselineToOverlineTextBaselineHeight = value
            requestLayout()
        }
    var twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight
        get() = helper.twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight
        set(value) {
            helper.twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight = value
            requestLayout()
        }
    var twoLineCenterAligned
        get() = helper.twoLineCenterAligned
        set(value) {
            helper.twoLineCenterAligned = value
            requestLayout()
        }

    var threeLineOverlineTextBaselineToTopHeight
        get() = helper.threeLineOverlineTextBaselineToTopHeight
        set(value) {
            helper.threeLineOverlineTextBaselineToTopHeight = value
            requestLayout()
        }
    var threeLinePrimaryTextBaselineToTopHeight
        get() = helper.threeLinePrimaryTextBaselineToTopHeight
        set(value) {
            helper.threeLinePrimaryTextBaselineToTopHeight = value
            requestLayout()
        }
    var threeLinePrimaryTextBaselineToOverlineTextBaselineHeight
        get() = helper.threeLinePrimaryTextBaselineToOverlineTextBaselineHeight
        set(value) {
            helper.threeLinePrimaryTextBaselineToOverlineTextBaselineHeight = value
            requestLayout()
        }
    var threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight
        get() = helper.threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight
        set(value) {
            helper.threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight = value
            requestLayout()
        }
    var threeLineCenterAligned
        get() = helper.threeLineCenterAligned
        set(value) {
            helper.threeLineCenterAligned = value
            requestLayout()
        }

    init {
        StyledAttributes.resolve(context, attrs, R.styleable.ListItem, defStyleAttr, defStyleRes) {
            primaryTextView.apply {
                text = getString(R.styleable.ListItem_title).also { helper.title = it }
                setTextAppearanceResource(
                    getResourceId(R.styleable.ListItem_primaryTextAppearance)
                )
                setTextColor(MaterialColors.getColor(context, android.R.attr.textColorPrimary, 0))
            }
            secondaryTextView.apply {
                text = getString(R.styleable.ListItem_text).also { helper.text = it }
                lineHeight = dp2px(context, 20f)
                setTextAppearanceResource(
                    getResourceId(R.styleable.ListItem_secondaryTextAppearance)
                )
                setTextColor(MaterialColors.getColor(context, android.R.attr.textColorSecondary, 0))
            }
            overlineTextView.text = getString(R.styleable.ListItem_overline).also {
                helper.overline = it
            }
            metaTextView.text = getString(R.styleable.ListItem_meta).also {
                helper.meta = it
            }
        }

        generateLayoutParamsAndAddView(primaryTextView)
        generateLayoutParamsAndAddView(secondaryTextView)
        generateLayoutParamsAndAddView(overlineTextView)
        generateLayoutParamsAndAddView(metaTextView)

        foregroundCompat = RippleUtils.createRippleDrawable(context)
        super.setOnClickListener {
            if (parent is ListItemGroup) (parent as ListItemGroup).onItemClicked(this)
            onClickListener?.onClick(this)
        }
    }

    override fun getPaddingStart(): Int {
        return helper.paddingStart
    }

    override fun getPaddingEnd(): Int {
        return helper.paddingStart
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        helper.paddingStart = start
        helper.paddingEnd = end
        requestLayout()
    }

    override fun onLayout(isChanged: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = measuredWidth
        val height = measuredHeight

        var startChildViewsLeft = 0
        startChildViews.forEach {
            val itemWidth = it.measuredWidth
            val itemHeight = it.measuredHeight
            val itemTop = (height - itemHeight) / 2
            val layoutParams = it.layoutParams as LayoutParams
            it.layout(
                startChildViewsLeft + layoutParams.leftMargin,
                if (layoutParams.isCenterAligned) itemTop else layoutParams.topMargin,
                startChildViewsLeft + layoutParams.leftMargin + itemWidth,
                (if (layoutParams.isCenterAligned) itemTop else layoutParams.topMargin) + itemHeight
            )
            startChildViewsLeft += (itemWidth + layoutParams.rightMargin + layoutParams.leftMargin)
        }

        var endChildViewsRight = width
        endChildViews.forEach {
            val itemWidth = it.measuredWidth
            val itemHeight = it.measuredHeight
            val itemTop = (height - itemHeight) / 2
            val layoutParams = it.layoutParams as LayoutParams
            it.layout(
                endChildViewsRight - layoutParams.rightMargin - itemWidth,
                if (layoutParams.isCenterAligned) itemTop else layoutParams.topMargin,
                endChildViewsRight - layoutParams.rightMargin,
                (if (layoutParams.isCenterAligned) itemTop else layoutParams.topMargin) + itemHeight
            )
            endChildViewsRight -= (itemWidth + layoutParams.leftMargin + layoutParams.rightMargin)
        }

        val textLeft = if (startChildViewsLeft == 0) {
            if (forceKeyline) keyline else paddingStart
        } else {
            if (forceKeyline) keyline.coerceAtLeast(startChildViewsLeft) else startChildViewsLeft
        }
        val textRight = endChildViewsRight - paddingEnd
        val textVerticalOffset = if (hasStartOrEndChildViews) dp2px(context, 4f) else 0

        when (lineMode) {
            LineMode.ONE_LINE -> {
                val textTop = (measuredHeight - primaryTextView.measuredHeight) / 2
                primaryTextView.layout(
                    textLeft,
                    textTop,
                    (textLeft + primaryTextView.measuredWidth).coerceAtMost(textRight),
                    textTop + primaryTextView.measuredHeight
                )
            }

            LineMode.TWO_LINE -> {
                val primaryTextTop =
                    textVerticalOffset + twoLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight
                val secondaryTextTop =
                    primaryTextTop + primaryTextView.firstBaselineToTopHeight + twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight - secondaryTextView.firstBaselineToTopHeight
                primaryTextView.layout(
                    textLeft,
                    primaryTextTop,
                    (textLeft + primaryTextView.measuredWidth).coerceAtMost(textRight),
                    primaryTextTop + primaryTextView.measuredHeight
                )
                secondaryTextView.layout(
                    textLeft,
                    secondaryTextTop,
                    (textLeft + secondaryTextView.measuredWidth).coerceAtMost(textRight),
                    secondaryTextTop + secondaryTextView.measuredHeight
                )
            }

            LineMode.TWO_LINE_WITH_OVERLINE -> {

            }

            LineMode.THREE_LINE -> {
                val primaryTextTop =
                    threeLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight
                val secondaryTextTop =
                    primaryTextTop + primaryTextView.firstBaselineToTopHeight + threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight - secondaryTextView.firstBaselineToTopHeight
                primaryTextView.layout(
                    textLeft,
                    primaryTextTop,
                    (textLeft + primaryTextView.measuredWidth).coerceAtMost(textRight),
                    primaryTextTop + primaryTextView.measuredHeight
                )
                secondaryTextView.layout(
                    textLeft,
                    secondaryTextTop,
                    (textLeft + secondaryTextView.measuredWidth).coerceAtMost(textRight),
                    secondaryTextTop + secondaryTextView.measuredHeight
                )
            }

            LineMode.THREE_LINE_WITH_OVERLINE -> {

            }
        }
        primaryTextView.width = primaryTextView.right - primaryTextView.left
        secondaryTextView.width = secondaryTextView.right - secondaryTextView.left
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        updateLineMode(measuredWidth)
        setMeasuredDimension(measuredWidth, getItemHeight())
        forEach {
            measureChild(it, widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?) = p is ListItem.LayoutParams

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?) =
        ListItem.LayoutParams(super.generateLayoutParams(p))

    override fun generateLayoutParams(attrs: AttributeSet?) =
        ListItem.LayoutParams(super.generateLayoutParams(attrs))

    override fun generateDefaultLayoutParams() =
        ListItem.LayoutParams(super.generateDefaultLayoutParams())

    override fun addView(view: View, index: Int, params: ViewGroup.LayoutParams) {
        if (view.layoutParams !is ListItem.LayoutParams) {
            view.layoutParams = ListItem.LayoutParams(params)
        }
        when ((view.layoutParams as ListItem.LayoutParams).slot) {
            Slot.NO_SLOT -> {
                Log.w(TAG, "Child views of ListItem must have a slot. ")
                null
            }

            Slot.CENTER -> centerChildViews
            Slot.START -> startChildViews
            Slot.END -> endChildViews
            Slot.END_DIVIDED -> endDividedChildViews
            Slot.TOP -> topChildViews
            Slot.BOTTOM -> bottomChildViews
        }?.add(view)
        super.addView(view, index, view.layoutParams)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }

    private fun getItemHeight(): Int {
        return if (hasStartOrEndChildViews) when (lineMode) {
            LineMode.ONE_LINE -> dp2px(context, 56f)
            LineMode.TWO_LINE, LineMode.TWO_LINE_WITH_OVERLINE -> dp2px(context, 72f)
            LineMode.THREE_LINE, LineMode.THREE_LINE_WITH_OVERLINE -> dp2px(context, 88f)
        } else when (lineMode) {
            LineMode.ONE_LINE -> dp2px(context, 48f)
            LineMode.TWO_LINE, LineMode.TWO_LINE_WITH_OVERLINE -> dp2px(context, 64f)
            LineMode.THREE_LINE, LineMode.THREE_LINE_WITH_OVERLINE -> dp2px(context, 88f)
        }
    }

    protected fun generateLayoutParamsAndAddView(
        view: View,
        width: Int = WRAP_CONTENT,
        height: Int = WRAP_CONTENT,
        slot: Slot = Slot.CENTER,
        initLayoutParams: ListItem.LayoutParams.() -> Unit = {}
    ) {
        view.layoutParams = ListItem.LayoutParams(width, height).apply {
            this.slot = slot
            initLayoutParams(this)
        }
        addView(view)
    }

    private fun getSecondaryTextViewLineCount(@Px secondaryTextViewWidth: Int): Int {
        val text = text
        if (text != null) {
            var lineCount = 0
            var index = 0
            var length = text.length
            while (index < length - 1) {
                index += secondaryTextView.paint.breakText(
                    text, index, length, true, secondaryTextViewWidth.toFloat(), null
                )
                lineCount++
            }
            return lineCount
        } else {
            throw IllegalArgumentException()
            TODO()
        }
    }

    private fun updateLineMode(@Px secondaryTextViewWidth: Int) {
        helper.lineMode = if (overline == null) {
            if (text == null) {
                LineMode.ONE_LINE
            } else {
                when (getSecondaryTextViewLineCount(secondaryTextViewWidth)) {
                    0 -> LineMode.ONE_LINE
                    1 -> LineMode.TWO_LINE
                    else -> LineMode.THREE_LINE
                }
            }
        } else if (text == null) {
            LineMode.TWO_LINE_WITH_OVERLINE
        } else {
            LineMode.THREE_LINE_WITH_OVERLINE
        }
        onLineModeUpdated(helper.lineMode)
    }

    protected open fun onLineModeUpdated(lineMode: LineMode) {}
}