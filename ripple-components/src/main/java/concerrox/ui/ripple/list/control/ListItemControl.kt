package concerrox.ui.ripple.list.control

import concerrox.ripple.R
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import concerrox.ui.ripple.list.item.ListItem
import concerrox.ui.view.StyledAttributes

open class ListItemControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayoutCompat(context) {

    var isPrimaryAction = false

    init {
        StyledAttributes.resolve(
            context, attrs, R.styleable.ListItemControl, defStyleAttr, defStyleRes
        ) {
            layoutParams = ListItem.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                ListItem.Slot.parse(getStringOrThrow(R.styleable.ListItemControl_layout_slot))?.let {
                    slot = it
                }
                isCenterAligned = true
            }
        }
    }

}