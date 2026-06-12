package concerrox.preference.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import concerrox.ripple.R
import concerrox.ui.ripple.list.item.ListItem

open class Preference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.listItemStyle,
    defStyleRes: Int = R.style.Widget_Ripple_Material2_ListItem,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    open val listItem = ListItem(context, attrs, defStyleAttr, defStyleRes)
    var key: String? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        throw IllegalStateException("A Preference will never be add to layout actually! ")
    }

    override fun addView(child: View?, index: Int, params: LayoutParams?) {
//        if (parent is PreferenceGroup) (parent as PreferenceGroup).addView(listItem, index, params)
    }

}