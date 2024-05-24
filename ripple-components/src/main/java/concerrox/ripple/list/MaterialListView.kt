package concerrox.ripple.list

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import concerrox.ripple.Material

class MaterialListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        0
    } else {
        0
    }
) : RecyclerView(context, attrs, defStyleAttr) {

}