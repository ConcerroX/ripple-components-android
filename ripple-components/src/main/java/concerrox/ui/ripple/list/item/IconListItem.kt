package concerrox.ui.ripple.list.item

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import concerrox.ripple.R
import concerrox.ui.view.StyledAttributes
import concerrox.ui.view.dp2px

class IconListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.iconListItemStyle,
    defStyleRes: Int = R.style.Widget_Ripple_Material2_IconListItem,
) : ListItem(context, attrs, defStyleAttr, defStyleRes) {

    val iconView = AppCompatImageView(context)
    var icon: Drawable?
        get() = iconView.drawable
        set(value) {
            iconView.setImageDrawable(value)
        }

    var iconTintList
        get() = iconView.imageTintList
        set(value) {
            iconView.imageTintList = value
        }

    init {
        StyledAttributes.resolve(
            context, attrs, R.styleable.IconListItem, defStyleAttr, defStyleRes
        ) {
            icon = getDrawable(R.styleable.IconListItem_icon)
        }
        generateLayoutParamsAndAddView(
            iconView, dp2px(context, 24f), dp2px(context, 24f), Slot.START
        ) {
            val margin = dp2px(context, 16f)
            setMargins(margin, margin, margin, margin)
        }
    }

    override fun onLineModeUpdated(lineMode: LineMode) {
        val layoutParams = iconView.layoutParams as LayoutParams
        layoutParams.isCenterAligned = when (lineMode) {
            LineMode.ONE_LINE -> true
            LineMode.TWO_LINE, LineMode.TWO_LINE_WITH_OVERLINE -> true
            LineMode.THREE_LINE, LineMode.THREE_LINE_WITH_OVERLINE -> false
        }
    }
}