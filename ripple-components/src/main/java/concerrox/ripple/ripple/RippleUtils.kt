package concerrox.ripple.ripple

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.ripple.RippleUtils

object RippleUtils {
    fun createRippleDrawable(context: Context): RippleDrawable {
        val themedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground, themedValue, true
        )
        return AppCompatResources.getDrawable(
            context, themedValue.resourceId
        )?.mutate() as RippleDrawable
    }

    fun createRippleDrawableWithMask(context: Context, mask: Drawable): RippleDrawable {
        val themedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground, themedValue, true
        )
        return (AppCompatResources.getDrawable(
            context, themedValue.resourceId
        )?.mutate() as RippleDrawable).apply {
            setDrawableByLayerId(android.R.id.mask, mask)
        }
    }

    @SuppressLint("RestrictedApi")
    fun convertToRippleColors(colors: ColorStateList): ColorStateList {
        return RippleUtils.convertToRippleDrawableColor(colors)
    }
}