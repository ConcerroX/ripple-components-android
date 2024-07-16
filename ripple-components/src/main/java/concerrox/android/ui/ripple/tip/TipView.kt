package concerrox.android.ui.ripple.tip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import com.google.android.material.shape.MaterialShapeDrawable
import concerrox.android.ui.view.StyledAttributes
import concerrox.ripple.Material
import concerrox.ripple.R
import concerrox.ripple.databinding.RippleTipViewBinding
import concerrox.ripple.internal.UiUtils.dp2px

class TipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tipViewStyle,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        R.style.Widget_Ripple_Material2_TipView_Information
    } else {
        R.style.Widget_Ripple_Material2_TipView_Information
    }
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = RippleTipViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        StyledAttributes.resolve(context, attrs, R.styleable.TipView, defStyleAttr, defStyleRes) {
            background = MaterialShapeDrawable(
                it.getShapeAppearanceModel(R.styleable.TipView_shapeAppearance)
            ).apply {
                tintList = it.getColorStateList(R.styleable.TipView_backgroundTint)
            }
            binding.icon.imageTintList = it.getColorStateList(R.styleable.TipView_iconTint)
            binding.title.setTextColor(it.getColorStateList(R.styleable.TipView_titleTextColor))
            binding.text.setTextColor(it.getColorStateList(R.styleable.TipView_textColor))
        }
        setPadding(dp2px(16f, context))
    }

}