package concerrox.preference.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import concerrox.ui.ripple.list.item.ListItem

open class Preference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    open val listItem = ListItem(context)
    var key: String? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        throw IllegalStateException("A preference will never be add to layout actually! ")
    }

}