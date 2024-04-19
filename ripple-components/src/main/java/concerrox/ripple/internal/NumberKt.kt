package concerrox.ripple.internal

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).roundToInt()

fun Int.toDp(): Int
    = (this / Resources.getSystem().displayMetrics.density).roundToInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)