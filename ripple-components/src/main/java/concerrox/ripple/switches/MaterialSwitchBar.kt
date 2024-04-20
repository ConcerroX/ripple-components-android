package concerrox.ripple.switches

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Checkable
import android.widget.CompoundButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.TextViewCompat
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView
import concerrox.ripple.Material
import concerrox.ripple.R
import concerrox.ripple.foreground.ForegroundLinearLayout
import concerrox.ripple.foreground.foregroundCompat
import concerrox.ripple.internal.ResourcesUtils.getShapeAppearanceModelResource
import concerrox.ripple.ripple.RippleUtils

class MaterialSwitchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialSwitchBarStyle,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        R.style.Widget_Ripple_Material3_SwitchBar
    } else {
        R.style.Widget_Ripple_Material2_SwitchBar
    }
) : ForegroundLinearLayout(context, attrs, defStyleAttr), Checkable {

    private val textView = MaterialTextView(context)
    private val switch: MaterialSwitch = MaterialSwitch(context)
    private var isChecked: Boolean = false
    private val checkedStateSet = intArrayOf(android.R.attr.state_checked)

    init {
        addView(textView, MATCH_PARENT, WRAP_CONTENT)
        addView(switch, WRAP_CONTENT, WRAP_CONTENT)
        gravity = Gravity.CENTER

        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.MaterialSwitchBar, defStyleAttr, defStyleRes
        )
        setPaddingRelative(
            0,
            0,
            typedArray.getDimensionPixelSize(R.styleable.MaterialSwitchBar_android_paddingEnd, 0),
            0
        )
        textView.apply {
            (layoutParams as LayoutParams).weight = 1f
            TextViewCompat.setTextAppearance(
                this, typedArray.getResourceId(
                    R.styleable.MaterialSwitchBar_android_textAppearance, 0
                )
            )
            setPaddingRelative(
                typedArray.getDimensionPixelSize(
                    R.styleable.MaterialSwitchBar_android_paddingStart, 0
                ), typedArray.getDimensionPixelSize(
                    R.styleable.MaterialSwitchBar_android_paddingTop, 0
                ), 0, typedArray.getDimensionPixelSize(
                    R.styleable.MaterialSwitchBar_android_paddingBottom, 0
                )
            )
        }

        background = MaterialShapeDrawable(
            getShapeAppearanceModelResource(
                context, typedArray.getResourceId(R.styleable.MaterialSwitchBar_shapeAppearance, 0)
            )
        ).apply {
            fillColor = typedArray.getColorStateList(R.styleable.MaterialSwitchBar_backgroundTint)
        }
        foregroundCompat = RippleUtils.createRippleDrawableWithMask(context, background)

        if (!Material.isMaterial3Enabled(context)) {
            switch.trackTintList = AppCompatResources.getColorStateList(
                context, R.color.material_switch_bar_switch_track_tint_m2
            )
            switch.thumbTintList = AppCompatResources.getColorStateList(
                context, R.color.material_switch_bar_switch_thumb_tint_m2
            )
            textView.setTextColor(
                MaterialColors.getColorOrNull(
                    context, com.google.android.material.R.attr.colorOnPrimary
                )!!
            )
            switch.haloDrawable?.setColor(
                RippleUtils.convertToRippleColors(ColorStateList.valueOf(0x1f000000))
            )
        }

        typedArray.recycle()

        switch.isClickable = false
        setOnClickListener {
            toggle()
        }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        background.setState(drawableState)
        invalidate()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked()) {
            mergeDrawableStates(drawableState, checkedStateSet)
        }
        return drawableState
    }

    var text: CharSequence?
        set(value) {
            textView.text = value
        }
        get() = textView.text

    override fun setChecked(checked: Boolean) {
        isChecked = checked
        switch.isChecked = checked
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        isChecked = !isChecked
        switch.toggle()
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        switch.setOnCheckedChangeListener(listener)
    }

}