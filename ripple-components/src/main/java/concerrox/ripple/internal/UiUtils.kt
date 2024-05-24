package concerrox.ripple.internal

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

object UiUtils {
    fun dp2px(dp: Number, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics
        ).roundToInt()
    }
}

fun Number.dp2px(context: Context): Int = UiUtils.dp2px(this, context)