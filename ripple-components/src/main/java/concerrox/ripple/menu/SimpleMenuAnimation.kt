package concerrox.ripple.menu

import android.animation.*
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import concerrox.ripple.drawable.BoundedDrawable
import kotlin.math.abs

/**
 * Helper class to create and start animation of Simple Menu.
 */
internal object SimpleMenuAnimation {
    fun startEnterAnimation(
        background: BoundedDrawable?,
        view: View,
        width: Int,
        height: Int,
        centerX: Int,
        centerY: Int,
        start: Rect,
        itemHeight: Int,
        elevation: Int,
        selectedIndex: Int
    ) {
        val holder = background?.let { PropertyHolder(it, view) }
        val backgroundAnimator = holder?.let {
            createBoundsAnimator(
                it, width, height, centerX, centerY, start
            )
        }
        val elevationAnimator = createElevationAnimator(view, elevation.toFloat())
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(backgroundAnimator, elevationAnimator)
        if (backgroundAnimator != null) {
            animatorSet.duration = backgroundAnimator.duration
        }
        animatorSet.start()
        val delay: Long = 0
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val offset = selectedIndex - i
                startChild(
                    view.getChildAt(i),
                    delay + 30 * abs(offset),
                    if (offset == 0) 0 else (itemHeight * 0.2).toInt() * if (offset < 0) -1 else 1
                )
            }
        }
    }

    private fun startChild(child: View, delay: Long, translationY: Int) {
        child.alpha = 0f
        val alphaAnimator: Animator = ObjectAnimator.ofFloat(child, "alpha", 0.0f, 1.0f)
        alphaAnimator.duration = 200
        alphaAnimator.interpolator = AccelerateInterpolator()
        val translationAnimator: Animator =
            ObjectAnimator.ofFloat(child, "translationY", translationY.toFloat(), 0f)
        translationAnimator.duration = 275
        translationAnimator.interpolator = DecelerateInterpolator()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, translationAnimator)
        animatorSet.startDelay = delay
        animatorSet.start()
    }

    private fun getBounds(
        width: Int, height: Int, centerX: Int, centerY: Int
    ): Array<Rect> {
        val endWidth = centerX.coerceAtLeast(width - centerX)
        val endHeight = centerY.coerceAtLeast(height - centerY)
        val endLeft = centerX - endWidth
        val endRight = centerX + endWidth
        val endTop = centerY - endHeight
        val endBottom = centerY + endHeight
        val end = Rect(endLeft, endTop, endRight, endBottom)
        val max = Rect(0, 0, width, height)
        return arrayOf(end, max)
    }

    private fun createBoundsAnimator(
        holder: PropertyHolder, width: Int, height: Int, centerX: Int, centerY: Int, start: Rect
    ): Animator {
        val speed = 4096
        val endWidth = centerX.coerceAtLeast(width - centerX)
        val endHeight = centerY.coerceAtLeast(height - centerY)
        val rect = getBounds(width, height, centerX, centerY)
        val end = rect[0]
        val max = rect[1]
        var duration = (endWidth.coerceAtLeast(endHeight).toFloat() / speed * 1000).toLong()
        duration = duration.coerceAtLeast(150)
        duration = duration.coerceAtMost(300)
        val animator: Animator = ObjectAnimator.ofObject(
            holder,
            SimpleMenuBoundsProperty.BOUNDS,
            RectEvaluator(max),
            start,
            end
        )
        animator.interpolator = DecelerateInterpolator()
        animator.duration = duration
        return animator
    }

    private fun createElevationAnimator(view: View, elevation: Float): Animator {
        val animator: Animator = ObjectAnimator.ofObject(
            view, "translationX", FloatEvaluator() as TypeEvaluator<*>, -elevation, 0f
        )
        animator.interpolator = FastOutSlowInInterpolator()
        return animator
    }
}