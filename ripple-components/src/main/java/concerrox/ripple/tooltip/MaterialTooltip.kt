package concerrox.ripple.tooltip

import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.constraintlayout.solver.widgets.ConstraintAnchor

class MaterialTooltip(private val anchor: View, private val text: CharSequence? = null) {

    private val tooltipHelper = MaterialTooltipHelper(this)
    val view = MaterialTooltipView(anchor.context)

    init {
        tooltipHelper.attachToView(anchor)
    }

    fun show() {

    }




}