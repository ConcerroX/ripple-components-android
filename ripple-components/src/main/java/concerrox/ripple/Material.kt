package concerrox.ripple

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.google.android.material.R
import com.google.android.material.color.MaterialColors

class Material {
    companion object {
        private const val INVALID_COLOR = Int.MIN_VALUE

        @JvmStatic
        fun isMaterialThemeEnabled(context: Context) =
            getAttrColor(context, R.attr.colorSurface) != INVALID_COLOR

        @JvmStatic
        fun isMaterial3Enabled(context: Context) =
            getAttrColor(context, R.attr.colorSurfaceContainer) != INVALID_COLOR

        @JvmStatic
        fun isMaterial2Enabled(context: Context) =
            isMaterialThemeEnabled(context) && !isMaterial3Enabled(context)

        @JvmStatic
        fun isMaterialYouEnabled(context: Context) = false

        @ColorInt
        @JvmStatic
        private fun getAttrColor(context: Context, @AttrRes attrResId: Int) =
            MaterialColors.getColor(context, attrResId, INVALID_COLOR)

    }
}