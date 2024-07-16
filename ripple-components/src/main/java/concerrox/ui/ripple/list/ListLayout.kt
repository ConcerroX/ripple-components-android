package concerrox.ui.ripple.list

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import concerrox.ui.ripple.list.item.ListItem

class ListLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    fun interface OnListItemClickListener {
        fun onClick(item: ListItem)
    }

    private val onListItemClickListeners = mutableListOf<OnListItemClickListener>()

    init {
        orientation = VERTICAL
    }

    fun addOnItemClickListener(listener: OnListItemClickListener) {
        onListItemClickListeners.add(listener)
    }

    fun removeOnItemClickListener(listener: OnListItemClickListener) {
        onListItemClickListeners.remove(listener)
    }

    fun clearOnItemClickListeners() {
        onListItemClickListeners.clear()
    }

    internal fun onItemClicked(item: ListItem) {
        onListItemClickListeners.forEach {
            it.onClick(item)
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is ListItemGroup) {
            super.addView(child, index, params)
        }
    }

}