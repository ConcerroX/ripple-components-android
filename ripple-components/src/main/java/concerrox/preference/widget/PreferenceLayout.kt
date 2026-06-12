package concerrox.preference.widget

import android.content.Context
import android.util.AttributeSet
import concerrox.ui.ripple.list.ListLayout

class PreferenceLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ListLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        addOnItemClickListener {

        }
    }

}