package concerrox.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.res.getBooleanOrThrow
import androidx.core.content.res.getDimensionPixelSizeOrThrow
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.content.res.getStringOrThrow
import com.google.android.material.shape.ShapeAppearanceModel

class StyledAttributes(
    private val context: Context,
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int,
    @StyleRes defStyleRes: Int
) {
    companion object {
        fun resolve(
            context: Context,
            set: AttributeSet?,
            @StyleableRes attrs: IntArray,
            @AttrRes defStyleAttr: Int,
            @StyleRes defStyleRes: Int,
            block: StyledAttributes.(styledAttrs: StyledAttributes) -> Unit
        ) {
            val styledAttrs = StyledAttributes(context, set, attrs, defStyleAttr, defStyleRes)
            block(styledAttrs, styledAttrs)
            styledAttrs.recycle()
        }
    }

    private val typedArray = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)

    fun getResourceId(@StyleableRes attr: Int): Int {
        return typedArray.getResourceId(attr, 0)
    }

    fun getString(@StyleableRes attr: Int): String? = typedArray.getString(attr)

    fun getStringOrThrow(@StyleableRes attr: Int): String = typedArray.getStringOrThrow(attr)

    fun getDrawable(@StyleableRes attr: Int): Drawable? = typedArray.getDrawable(attr)

    fun getDrawableOrThrow(@StyleableRes attr: Int): Drawable = typedArray.getDrawableOrThrow(attr)

    fun getBoolean(@StyleableRes attr: Int): Boolean? {
        return if (typedArray.hasValue(attr)) typedArray.getBoolean(attr, true) else null
    }

    fun getDimensionPixelSize(@StyleableRes attr: Int): Int {
        if (!typedArray.hasValue(attr)) {
            throw IllegalArgumentException("Attribute $attr not defined in set.")
        }
        return typedArray.getDimensionPixelSizeOrThrow(attr)
    }

    fun getColorStateList(@StyleableRes attr: Int): ColorStateList {
        return typedArray.getColorStateList(attr)!!
    }

    fun getShapeAppearanceModel(@StyleableRes attr: Int): ShapeAppearanceModel {
        return ShapeAppearanceModel.builder(
            context, getResourceId(attr), 0
        ).build()
    }

    private fun recycle() {
        typedArray.recycle()
    }
}