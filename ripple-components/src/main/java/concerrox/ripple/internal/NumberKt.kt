package concerrox.ripple.internal

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.pow
import kotlin.math.roundToInt

@Deprecated("")
val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).roundToInt()

@Deprecated("")
fun Int.toDp(): Int
    = (this / Resources.getSystem().displayMetrics.density).roundToInt()

@Deprecated("")
val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

@Deprecated("")
infix fun Float.pow(exponent: Int): Float = toDouble().pow(exponent).toFloat()

@Deprecated("")
infix fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()

