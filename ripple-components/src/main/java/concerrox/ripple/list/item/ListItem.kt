package concerrox.ripple.list.item

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.forEach
import com.google.android.material.color.MaterialColors
import com.google.android.material.textview.MaterialTextView
import concerrox.ripple.Material
import concerrox.ripple.R
import concerrox.ripple.foreground.ForegroundViewGroup
import concerrox.ripple.foreground.foregroundCompat
import concerrox.ripple.internal.dp2px
import concerrox.ripple.internal.setTextAppearanceResource
import concerrox.ripple.internal.useAndRecycle
import concerrox.ripple.ripple.RippleUtils


class ListItem @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.listItemStyle,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        1
    } else {
        0
    }
) : ForegroundViewGroup(context, attrs) {

    private lateinit var primaryTextView: MaterialTextView
    private lateinit var secondaryTextView: MaterialTextView
    private lateinit var overlineTextView: MaterialTextView
    private lateinit var metaTextView: MaterialTextView

    private var _paddingStart = 0
    private var _paddingEnd = 0

    private var _forceKeyline = false
    private var _keyline = 1

    private var _title: String? = null
    private var _text: String? = null
    private var _meta: String? = null
    private var _overline: String? = null

    private var _twoLineOverlineTextBaselineToTopHeight = 0
    private var _twoLinePrimaryTextBaselineToTopHeight = 0
    private var _twoLinePrimaryTextBaselineToOverlineTextBaselineHeight = 0
    private var _twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight = 0
    private var _twoLineCenterAligned = false

    private var _threeLineOverlineTextBaselineToTopHeight = 0
    private var _threeLinePrimaryTextBaselineToTopHeight = 0
    private var _threeLinePrimaryTextBaselineToOverlineTextBaselineHeight = 0
    private var _threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight = 0
    private var _threeLineCenterAligned = false

    private var _lineMode = LineMode.TWO_LINE

    init {
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.ListItem, defStyleAttr, defStyleRes)
        attributes.useAndRecycle {
            primaryTextView = MaterialTextView(context).apply {
                maxLines = 1
                text = it.getString(R.styleable.ListItem_title)
                setTextAppearanceResource(
                    it.getResourceId(
                        R.styleable.ListItem_primaryTextAppearance, 0
                    )
                )
                setTextColor(MaterialColors.getColor(context, android.R.attr.textColorPrimary, 0))
            }
            secondaryTextView = MaterialTextView(context).apply {
                maxLines = 2
                text = it.getString(R.styleable.ListItem_text)
                setTextAppearanceResource(
                    it.getResourceId(
                        R.styleable.ListItem_secondaryTextAppearance, 0
                    )
                )
                setTextColor(MaterialColors.getColor(context, android.R.attr.textColorSecondary, 0))
            }
            overlineTextView = MaterialTextView(context).apply {
                maxLines = 1
                text = it.getString(R.styleable.ListItem_overline)
            }
            metaTextView = MaterialTextView(context).apply {
                maxLines = 1
                text = it.getString(R.styleable.ListItem_meta)
            }
            _paddingStart = it.getDimensionPixelSize(
                R.styleable.ListItem_android_paddingStart, 0
            )
            _paddingEnd = it.getDimensionPixelSize(
                R.styleable.ListItem_android_paddingStart, 0
            )
            _keyline = it.getDimensionPixelSize(
                R.styleable.ListItem_keyline, 0
            )

            _twoLineOverlineTextBaselineToTopHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_twoLineOverlineTextBaselineToTopHeight, 0
            )
            _twoLinePrimaryTextBaselineToTopHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_twoLinePrimaryTextBaselineToTopHeight, 0
            )
            _twoLinePrimaryTextBaselineToOverlineTextBaselineHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_twoLinePrimaryTextBaselineToOverlineTextBaselineHeight, 0
            )
            _twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight, 0
            )
            _twoLineCenterAligned = it.getBoolean(
                R.styleable.ListItem_twoLineCenterAligned, false
            )

            _threeLineOverlineTextBaselineToTopHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_threeLineOverlineTextBaselineToTopHeight, 0
            )
            _threeLinePrimaryTextBaselineToTopHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_threeLinePrimaryTextBaselineToTopHeight, 0
            )
            _threeLinePrimaryTextBaselineToOverlineTextBaselineHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_threeLinePrimaryTextBaselineToOverlineTextBaselineHeight, 0
            )
            _threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight = it.getDimensionPixelSize(
                R.styleable.ListItem_threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight, 0
            )
            _threeLineCenterAligned = it.getBoolean(
                R.styleable.ListItem_threeLineCenterAligned, false
            )
        }
        addView(primaryTextView)
        addView(secondaryTextView)
        addView(overlineTextView)
        addView(metaTextView)

        foregroundCompat = RippleUtils.createRippleDrawable(context)
        setOnClickListener {

        }
    }

    override fun onLayout(isChanged: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        when (_lineMode) {
            LineMode.ONE_LINE -> {
                primaryTextView.layout(
                    if (_forceKeyline) _keyline else _paddingStart,
                    _twoLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight,
                    right - _paddingEnd,
                    _twoLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight + primaryTextView.measuredHeight
                )
            }

            LineMode.TWO_LINE -> {
                primaryTextView.layout(
                    if (_forceKeyline) _keyline else _paddingStart,
                    _twoLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight,
                    right - _paddingEnd,
                    _twoLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight + primaryTextView.measuredHeight
                )
                secondaryTextView.layout(
                    if (_forceKeyline) _keyline else _paddingStart,
                    primaryTextView.top + primaryTextView.firstBaselineToTopHeight + _twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight - secondaryTextView.firstBaselineToTopHeight,
                    right - _paddingEnd,
                    primaryTextView.top + primaryTextView.firstBaselineToTopHeight + _twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight - secondaryTextView.firstBaselineToTopHeight + secondaryTextView.measuredHeight
                )
            }

            LineMode.TWO_LINE_WITH_OVERLINE -> {

            }

            LineMode.THREE_LINE -> {
                primaryTextView.layout(
                    if (_forceKeyline) _keyline else _paddingStart,
                    _threeLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight,
                    right - _paddingEnd,
                    _threeLinePrimaryTextBaselineToTopHeight - primaryTextView.firstBaselineToTopHeight + primaryTextView.measuredHeight
                )
                secondaryTextView.layout(
                    if (_forceKeyline) _keyline else _paddingStart,
                    primaryTextView.top + primaryTextView.firstBaselineToTopHeight + _threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight - secondaryTextView.firstBaselineToTopHeight,
                    right - _paddingEnd,
                    primaryTextView.top + primaryTextView.firstBaselineToTopHeight + _threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight - secondaryTextView.firstBaselineToTopHeight + secondaryTextView.measuredHeight
                )
            }

            LineMode.THREE_LINE_WITH_OVERLINE -> {

            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        println(this.toString()+" "+measuredHeight.toString())
//        println(widthMeasureSpec.toString()+heightMeasureSpec.toString())
        updateLineMode()
        println(this.toString()+_lineMode)
//        println(secondaryTextView.text.toString() + _lineMode.toString())
//        val itemHeight = when (_lineMode) {
//            LineMode.ONE_LINE -> 48.dp2px(context)
//            LineMode.TWO_LINE, LineMode.TWO_LINE_WITH_OVERLINE -> 64.dp2px(context)
//            LineMode.THREE_LINE, LineMode.THREE_LINE_WITH_OVERLINE -> 88.dp2px(context)
//        }
//        val itemHeightMeasureSpec = MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY)
//        super.onMeasure(widthMeasureSpec, itemHeightMeasureSpec)
//        forEach {
//            measureChild(it, widthMeasureSpec, heightMeasureSpec)
//        }
        setMeasuredDimension(measuredWidth, 10)
    }

    private fun updateLineMode() {
        if (overlineTextView.text.isEmpty()) {
            secondaryTextView.width =
                measuredWidth - _paddingEnd - if (_forceKeyline) _keyline else _paddingStart
            if (secondaryTextView.text.isBlank()) {
                _lineMode = LineMode.ONE_LINE
                return
            }
            _lineMode = when (secondaryTextView.lineCount) {
                0 -> LineMode.ONE_LINE
                1 -> LineMode.TWO_LINE
                2 -> LineMode.THREE_LINE
                else -> {
                    throw IllegalArgumentException("Do not modify the maxLines of secondaryTextView! ")
                }
            }
        } else {
            secondaryTextView.maxLines = 1
            _lineMode = if (secondaryTextView.text.isEmpty()) {
                LineMode.TWO_LINE_WITH_OVERLINE
            } else {
                LineMode.THREE_LINE_WITH_OVERLINE
            }
        }
    }

    enum class LineMode {
        ONE_LINE, TWO_LINE, TWO_LINE_WITH_OVERLINE, THREE_LINE, THREE_LINE_WITH_OVERLINE
    }
}