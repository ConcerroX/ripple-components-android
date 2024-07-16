package concerrox.ui.ripple.list.control

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import concerrox.ripple.switches.MaterialSwitch
import concerrox.ui.ripple.list.item.ListItem
import concerrox.ui.view.dp2px

class SwitchControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ListItemControl(context, attrs, defStyleAttr, defStyleRes) {

    private val switchView = MaterialSwitch(context)

    init {
        addView(switchView, WRAP_CONTENT, WRAP_CONTENT)
        (layoutParams as ListItem.LayoutParams).apply {
            setMargins(0, 0, dp2px(context, 16f), 0)
        }
    }

}