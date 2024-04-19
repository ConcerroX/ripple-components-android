package concerrox.ripple.menu

import android.animation.TypeEvaluator
import android.annotation.SuppressLint
import android.graphics.Rect

/**
 * This evaluator can be used to perform type interpolation between [Rect].
 */
internal class RectEvaluator(private val mMax: Rect) : TypeEvaluator<Rect?> {
    private val temp = Rect()

    @SuppressLint("CheckResult")
    override fun evaluate(fraction: Float, startValue: Rect?, endValue: Rect?): Rect? {
        return if (startValue == null || endValue == null) {
            null
        } else {
            temp.left = startValue.left + ((endValue.left - startValue.left) * fraction).toInt()
            temp.top = startValue.top + ((endValue.top - startValue.top) * fraction).toInt()
            temp.right = startValue.right + ((endValue.right - startValue.right) * fraction).toInt()
            temp.bottom =
                startValue.bottom + ((endValue.bottom - startValue.bottom) * fraction).toInt()
            temp.setIntersect(mMax, temp)
            temp
        }
    }
}