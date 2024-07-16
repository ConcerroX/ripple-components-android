@file:JvmName("UiUtils")

package concerrox.ui.view

import android.content.Context
import android.util.TypedValue

fun dp2px(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
    ).toInt()
}