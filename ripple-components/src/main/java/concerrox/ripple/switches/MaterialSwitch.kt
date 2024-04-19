package concerrox.ripple.switches

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import concerrox.ripple.Material

class MaterialSwitch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val realM3Switch: MaterialSwitch = MaterialSwitch(context)
    private val realM2Switch: SwitchMaterial = SwitchMaterial(context)
    private val isMaterial3Enabled = Material.isMaterial3Enabled(context)

    init {
        if (isMaterial3Enabled) {
            addView(realM3Switch)
        } else {
            addView(realM2Switch)
        }
    }

    fun toggle() = if (isMaterial3Enabled) {
        realM3Switch.toggle()
    } else {
        realM2Switch.toggle()
    }

    override fun setClickable(clickable: Boolean) = if (isMaterial3Enabled) {
        realM3Switch.setClickable(clickable)
    } else {
        realM2Switch.setClickable(clickable)
    }
}