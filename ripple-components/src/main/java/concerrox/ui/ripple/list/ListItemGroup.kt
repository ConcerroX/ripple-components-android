package concerrox.ui.ripple.list

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import concerrox.ui.ripple.list.item.ListItem

class ListItemGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    internal fun onItemClicked(item: ListItem) {
        if (parent is ListLayout) (parent as ListLayout).onItemClicked(item)
    }

//    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
//        super.addView(child, index, params)
//    }

}