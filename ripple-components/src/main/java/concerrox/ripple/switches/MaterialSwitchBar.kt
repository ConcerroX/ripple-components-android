package concerrox.ripple.switches

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import android.widget.CompoundButton
import android.widget.FrameLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import concerrox.ripple.R
import concerrox.ripple.databinding.MaterialSwitchBarBinding
import concerrox.ripple.internal.dp

class MaterialSwitchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int? = 0
) : FrameLayout(context), Checkable {

    //    private val binding = MaterialSwitchBarBinding.inflate(LayoutInflater.from(context))
    private var switch: MaterialSwitch

    init {
        LayoutInflater.from(context).inflate(R.layout.material_switch_bar, this)
        switch = findViewById(R.id.switch_bar_switch)
        switch.isClickable = false
        findViewById<MaterialCardView>(R.id.switch_bar_card).setOnClickListener {
            switch.toggle()
        }
    }

    var text: CharSequence?
        set(value) {
            findViewById<MaterialTextView>(R.id.switch_bar_text).text = value
        }
        get() = findViewById<MaterialTextView>(R.id.switch_bar_text).text

    override fun setChecked(checked: Boolean) {
        switch.isChecked = checked
    }

    override fun isChecked(): Boolean {
        return switch.isChecked
    }

    override fun toggle() {
        switch.toggle()
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        switch.setOnCheckedChangeListener(listener)
    }

}