package concerrox.ripple.spinner

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textview.MaterialTextView
import concerrox.ripple.Material
import concerrox.ripple.foreground.ForegroundLinearLayout
import concerrox.ripple.internal.ResourcesUtils
import concerrox.ripple.internal.dp
import concerrox.ripple.menu.SimpleMenuPopupWindow
import concerrox.ripple.ripple.RippleUtils
import concerrox.ripple.foreground.foregroundCompat

@SuppressLint("PrivateResource")
class MaterialSpinner @JvmOverloads constructor(
    private val context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ForegroundLinearLayout(context, attrs, defStyleAttr) {

    var entries: List<CharSequence> = listOf()
        set(value) {
            textView.text = value.firstOrNull()
            menuPopup.entries = value
            field = value
        }

    val textView = MaterialTextView(context, null, com.google.android.material.R.attr.textAppearanceBody1).apply {
        layoutParams = LayoutParams(WRAP_CONTENT, MATCH_PARENT).apply {
            setMargins(16.dp, 0, 0, 0)
            weight = 1f
        }
        gravity = Gravity.CENTER or Gravity.START
    }

    val icon = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(24.dp, 24.dp).apply {
            setMargins(0, 12.dp, 12.dp, 12.dp)
        }
        setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp)
        setColorFilter(MaterialColors.getColorOrNull(context, android.R.attr.textColorSecondary)!!)
    }

    val menuPopup = SimpleMenuPopupWindow(context)
    var selectedIndex: Int = 0

    init {
        isClickable = true
        background = MaterialShapeDrawable(
            if (Material.isMaterial3Enabled(context)) {
                ResourcesUtils.getShapeAppearanceModel(
                    context, com.google.android.material.R.attr.shapeAppearanceCornerExtraSmall
                )
            } else {
                ResourcesUtils.getShapeAppearanceModel(
                    context, com.google.android.material.R.attr.shapeAppearanceSmallComponent
                )
            }
        ).apply {
            fillColor = AppCompatResources.getColorStateList(
                context,
                com.google.android.material.R.color.mtrl_textinput_filled_box_default_background_color
            )
        }
        addView(textView)
        addView(icon)

        foregroundCompat = RippleUtils.createRippleDrawable(context)
            .apply { setDrawableByLayerId(android.R.id.mask, background) }

        setOnClickListener {
            menuPopup.show(this, this.parent as View, 0)
        }
        menuPopup.onItemClickListener = object : SimpleMenuPopupWindow.OnItemClickListener {
            override fun onClick(position: Int) {
                select(position)
            }
        }
    }

    fun select(position: Int) {
        selectedIndex = position
        menuPopup.selectedIndex = position
        textView.text = entries[position]
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams.height = 48.dp
    }

}