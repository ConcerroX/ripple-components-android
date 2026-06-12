package concerrox.preference.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import concerrox.ripple.R
import concerrox.ripple.menu.SimpleMenuPopupWindow
import concerrox.ui.view.dp2px

class SimpleMenuPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.listItemStyle,
    defStyleRes: Int = R.style.Widget_Ripple_Material2_ListItem,
) : Preference(context, attrs, defStyleAttr, defStyleRes) {

    init {
        listItem.setOnClickListener {
            val menu = SimpleMenuPopupWindow(context)
            menu.entries = arrayListOf("A")
            menu.requestMeasure()
            menu.show(it, it.parent.parent as View, dp2px(context, 16f))
        }
    }
}