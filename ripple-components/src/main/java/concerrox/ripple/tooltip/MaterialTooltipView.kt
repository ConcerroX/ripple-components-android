package concerrox.ripple.tooltip

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.tooltip.TooltipDrawable
import concerrox.ripple.Material
import concerrox.ripple.R
import concerrox.ripple.internal.ResourcesUtils
import concerrox.ripple.internal.setTextAppearanceResource
import concerrox.ripple.internal.useAndRecycle


class MaterialTooltipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialTooltipStyle,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        R.style.Widget_Ripple_Material2_MaterialTooltip
    } else {
        R.style.Widget_Ripple_Material2_MaterialTooltip
    }
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.MaterialTooltipView, defStyleAttr, defStyleRes
        ).useAndRecycle {
            val onBackground = MaterialColors.getColor(
                context, com.google.android.material.R.attr.colorOnBackground, TooltipDrawable::class.java.getCanonicalName()
            )
            var backgroundc = MaterialColors.getColor(
                context, android.R.attr.colorBackground, TooltipDrawable::class.java.getCanonicalName()
            )
            val backgroundTintDefault = MaterialColors.layer(
                ColorUtils.setAlphaComponent(backgroundc, (0.9f * 255).toInt()),
                ColorUtils.setAlphaComponent(onBackground, (0.6f * 255).toInt())
            )
            background = MaterialShapeDrawable(
                ResourcesUtils.getShapeAppearanceModelResource(
                    context, it.getResourceId(
                        R.styleable.MaterialTooltipView_shapeAppearance, 0
                    )
                )
            ).apply {
                fillColor = ColorStateList.valueOf(backgroundTintDefault
                )
            }
            addView(MaterialTextView(context).apply {
                layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
//                    setMargins(it.getDimensionPixelSize(R.styleable.MaterialTooltipView_tooltipMargin, 0))
                }
                text = "TESTTTT"
                maxLines = it.getInteger(R.styleable.MaterialTooltipView_android_maxLines, 0)
                ellipsize = TextUtils.TruncateAt.END
//                maxWidth = it.getDimensionPixelSize(R.styleable.MaterialTooltipView_android_maxWidth, 0)
//                minWidth = it.getDimensionPixelSize(R.styleable.MaterialTooltipView_android_minWidth, 0)
//                minHeight = it.getDimensionPixelSize(R.styleable.MaterialTooltipView_android_minHeight, 0)
                setTextAppearanceResource(it.getResourceId(R.styleable.MaterialTooltipView_tooltipTextAppearance, 0))
                setPaddingRelative(
                    it.getDimensionPixelSize(R.styleable.MaterialTooltipView_tooltipPaddingHorizontal, 0),
                    it.getDimensionPixelSize(R.styleable.MaterialTooltipView_tooltipPaddingVertical, 0),
                    it.getDimensionPixelSize(R.styleable.MaterialTooltipView_tooltipPaddingHorizontal, 0),
                    it.getDimensionPixelSize(R.styleable.MaterialTooltipView_tooltipPaddingVertical, 0)
                )
            })
        }
    }

}