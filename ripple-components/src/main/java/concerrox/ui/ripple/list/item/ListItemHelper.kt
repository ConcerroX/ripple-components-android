package concerrox.ui.ripple.list.item

import android.util.AttributeSet
import concerrox.ripple.R
import concerrox.ui.ripple.list.item.ListItem.LineMode
import concerrox.ui.view.StyledAttributes

class ListItemHelper @JvmOverloads constructor(
    listItem: ListItem,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.listItemStyle,
    defStyleRes: Int = R.style.Widget_Ripple_Material2_ListItem,
) {

    internal var forceKeyline = false
    internal var keyline = 0
    internal var paddingStart = 0
    internal var paddingEnd = 0

    internal var title: String? = null
    internal var text: String? = null
    internal var meta: String? = null
    internal var overline: String? = null

    internal var twoLineOverlineTextBaselineToTopHeight = 0
    internal var twoLinePrimaryTextBaselineToTopHeight = 0
    internal var twoLinePrimaryTextBaselineToOverlineTextBaselineHeight = 0
    internal var twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight = 0
    internal var twoLineCenterAligned = false

    internal var threeLineOverlineTextBaselineToTopHeight = 0
    internal var threeLinePrimaryTextBaselineToTopHeight = 0
    internal var threeLinePrimaryTextBaselineToOverlineTextBaselineHeight = 0
    internal var threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight = 0
    internal var threeLineCenterAligned = false

    internal var lineMode = LineMode.ONE_LINE

    init {
        StyledAttributes.resolve(
            listItem.context, attrs, R.styleable.ListItem, defStyleAttr, defStyleRes
        ) {
            forceKeyline = getBoolean(R.styleable.ListItem_forceKeyline) == true
            keyline = getDimensionPixelSize(R.styleable.ListItem_keyline)
            paddingStart = getDimensionPixelSize(R.styleable.ListItem_android_paddingStart)
            paddingEnd = getDimensionPixelSize(R.styleable.ListItem_android_paddingEnd)

            twoLineOverlineTextBaselineToTopHeight = getDimensionPixelSize(
                R.styleable.ListItem_twoLineOverlineTextBaselineToTopHeight
            )
            twoLinePrimaryTextBaselineToTopHeight = getDimensionPixelSize(
                R.styleable.ListItem_twoLinePrimaryTextBaselineToTopHeight
            )
            twoLinePrimaryTextBaselineToOverlineTextBaselineHeight = getDimensionPixelSize(
                R.styleable.ListItem_twoLinePrimaryTextBaselineToOverlineTextBaselineHeight
            )
            twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight = getDimensionPixelSize(
                R.styleable.ListItem_twoLineSecondaryTextBaselineToPrimaryTextBaselineHeight
            )
            twoLineCenterAligned = getBoolean(R.styleable.ListItem_twoLineCenterAligned) == true

            threeLineOverlineTextBaselineToTopHeight = getDimensionPixelSize(
                R.styleable.ListItem_threeLineOverlineTextBaselineToTopHeight
            )
            threeLinePrimaryTextBaselineToTopHeight = getDimensionPixelSize(
                R.styleable.ListItem_threeLinePrimaryTextBaselineToTopHeight
            )
            threeLinePrimaryTextBaselineToOverlineTextBaselineHeight = getDimensionPixelSize(
                R.styleable.ListItem_threeLinePrimaryTextBaselineToOverlineTextBaselineHeight
            )
            threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight = getDimensionPixelSize(
                R.styleable.ListItem_threeLineSecondaryTextBaselineToPrimaryTextBaselineHeight
            )
            threeLineCenterAligned = getBoolean(R.styleable.ListItem_threeLineCenterAligned) == true
        }
    }
}