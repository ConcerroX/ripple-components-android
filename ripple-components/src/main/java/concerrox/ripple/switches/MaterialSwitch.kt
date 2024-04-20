package concerrox.ripple.switches

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import concerrox.ripple.Material

class MaterialSwitch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), Checkable {

    private var realSwitch: SwitchCompat
    private val isMaterial3Enabled = Material.isMaterial3Enabled(context)

    init {
        realSwitch = if (isMaterial3Enabled) {
            MaterialSwitch(context)
        } else {
            SwitchMaterial(context)
        }
        addView(realSwitch)
    }

    fun setUseMaterialThemeColors(useMaterialThemeColors: Boolean) {
        if (!isMaterial3Enabled) (realSwitch as SwitchMaterial).isUseMaterialThemeColors =
            useMaterialThemeColors
    }

    fun isUseMaterialThemeColors(): Boolean {
        return if (!isMaterial3Enabled) (realSwitch as SwitchMaterial).isUseMaterialThemeColors else true
    }

    override fun invalidate() {
        super.invalidate()
        realSwitch.invalidate()
    }

    var thumbDrawable: Drawable?
        set(value) {
            realSwitch.thumbDrawable = value
        }
        get() = realSwitch.thumbDrawable

    var thumbTintList: ColorStateList?
        set(value) {
            realSwitch.thumbTintList = value
        }
        get() = realSwitch.thumbTintList

    var thumbTintMode: PorterDuff.Mode?
        set(value) {
            realSwitch.thumbTintMode = value
        }
        get() = realSwitch.thumbTintMode

    fun setThumbIconResource(@DrawableRes resId: Int) {
        if (isMaterial3Enabled) (realSwitch as MaterialSwitch).setThumbIconResource(resId)
    }

    var thumbIconDrawable: Drawable?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconDrawable = value
        }
        get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconDrawable else null

    var thumbIconSize: Int?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconSize = value!!
        }
        @Px get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconSize else null

    var thumbIconTintList: ColorStateList?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconTintList = value
        }
        get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconTintList else null

    var thumbIconTintMode: PorterDuff.Mode?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconTintMode = value!!
        }
        get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).thumbIconTintMode else null

    var trackDrawable: Drawable?
        set(value) {
            realSwitch.trackDrawable = value
        }
        get() = realSwitch.trackDrawable

    var trackTintList: ColorStateList?
        set(value) {
            realSwitch.trackTintList = value
        }
        get() = realSwitch.trackTintList

    var trackTintMode: PorterDuff.Mode?
        set(value) {
            realSwitch.trackTintMode = value
        }
        get() = realSwitch.trackTintMode

    fun setTrackDecorationResource(@DrawableRes resId: Int) {
        if (isMaterial3Enabled) (realSwitch as MaterialSwitch).setTrackDecorationResource(resId)
    }

    var trackDecorationDrawable: Drawable?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).trackDecorationDrawable = value!!
        }
        get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).trackDecorationDrawable else null

    var trackDecorationTintList: ColorStateList?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).trackDecorationTintList = value!!
        }
        get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).trackDecorationTintList else null

    var trackDecorationTintMode: PorterDuff.Mode?
        set(value) {
            if (isMaterial3Enabled) (realSwitch as MaterialSwitch).trackDecorationTintMode = value!!
        }
        get() = if (isMaterial3Enabled) (realSwitch as MaterialSwitch).trackDecorationTintMode else null

    override fun setChecked(checked: Boolean) {
        realSwitch.isChecked = checked
    }

    override fun isChecked(): Boolean {
        return realSwitch.isChecked
    }

    override fun toggle() {
        realSwitch.toggle()
    }

    override fun setClickable(clickable: Boolean) {
        realSwitch.isClickable = clickable
        super.setClickable(clickable)

    }

    override fun isClickable(): Boolean {
        return super.isClickable()
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        realSwitch.setOnCheckedChangeListener(listener)
    }

    override fun setBackground(background: Drawable?) {
        realSwitch.background = background
    }

    override fun getBackground(): Drawable {
        return realSwitch.background
    }

    var haloDrawable: RippleDrawable?
        set(value) {
            realSwitch.background = value
        }
        get() = realSwitch.background as RippleDrawable
}