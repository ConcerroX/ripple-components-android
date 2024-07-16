package concerrox.preference.widget

import android.content.Context
import android.util.AttributeSet
import concerrox.ui.ripple.list.item.IconListItem
import concerrox.ui.ripple.list.item.ListItem

class IconPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : Preference(context, attrs, defStyleAttr, defStyleRes) {

    override val listItem = IconListItem(context)
}