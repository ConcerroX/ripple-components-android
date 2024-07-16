package concerrox.android.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import com.google.android.material.shape.ShapeAppearanceModel
import concerrox.ripple.R
import concerrox.ripple.internal.ResourcesUtils

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
            block: (styledAttrs: StyledAttributes) -> Unit
        ) {
            val styledAttrs = StyledAttributes(context, set, attrs, defStyleAttr, defStyleRes)
            block(styledAttrs)
            styledAttrs.recycle()
        }
    }

    private val typedArray = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)

    fun getResourceId(@StyleableRes attr: Int): Int {
        return typedArray.getResourceId(attr, 0)
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