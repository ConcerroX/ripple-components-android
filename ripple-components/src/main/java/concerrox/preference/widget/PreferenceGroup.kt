package concerrox.preference.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import concerrox.ui.ripple.list.ListItemGroup

class PreferenceGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ListItemGroup(context, attrs, defStyleAttr, defStyleRes) {

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is Preference) super.addView(child.listItem, index, params)
    }

}