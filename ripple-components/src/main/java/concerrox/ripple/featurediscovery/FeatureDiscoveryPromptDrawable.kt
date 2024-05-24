package concerrox.ripple.featurediscovery

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.drawable.GradientDrawable
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel

internal class FeatureDiscoveryPromptDrawable(private val promptView: FeatureDiscoveryPromptView) :
    GradientDrawable() {

    private companion object {
        val CIRCULAR_SHAPE_APPEARANCE_MODEL =
            ShapeAppearanceModel.builder().setAllCornerSizes(RelativeCornerSize(0.5f)).build()
    }

    //                    ), 100//245
    private val tapTargetRadiusAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 425
        interpolator = FastOutSlowInInterpolator()
    }
    private val promptBackgroundRadiusAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 375
        interpolator = FastOutSlowInInterpolator()
    }
    internal var innerCircleCenterX = 0f
    internal var centerY = 0f
    internal var outerCircleRadius = 0f

    internal var tapTargetCenter = PointF(0f, 0f)
    internal var tapTargetRadius = 0f

    internal var promptBackgroundCenter = PointF(0f, 0f)
    internal var promptBackgroundRadius = 0f

    internal fun show() {
        val realPromptBackgroundRadius = promptBackgroundRadius
        val realTapTargetRadius = tapTargetRadius
//        AnimatorSet().start()
        tapTargetRadiusAnimator.apply {
            removeAllUpdateListeners()
            addUpdateListener {
                val i = it.animatedValue as Float
                tapTargetRadius = realTapTargetRadius * i
            }
            start()
        }
        promptBackgroundRadiusAnimator.apply {
            removeAllUpdateListeners()
            addUpdateListener {
                val i = it.animatedValue as Float
                promptBackgroundRadius = realPromptBackgroundRadius * i
                invalidateSelf()
                promptView.invalidateOutline()
            }
            start()
        }
    }

    internal var radius = 0f

    private var path = Path()

    override fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            color = MaterialColors.getColor(
                promptView.context, com.google.android.material.R.attr.colorPrimary, 0
            )
            alpha = 245
        }
        val outerCirclePath = Path().apply {
            addCircle(
                promptBackgroundCenter.x,
                promptBackgroundCenter.y,
                promptBackgroundRadius,
                Path.Direction.CW
            )
        }
        val tapTargetPath = Path().apply {
            addCircle(
                tapTargetCenter.x, tapTargetCenter.y, tapTargetRadius, Path.Direction.CW
            )
        }
        outerCirclePath.op(tapTargetPath, Path.Op.DIFFERENCE)
        path = outerCirclePath
        canvas.drawPath(outerCirclePath, paint)
    }

    override fun getOutline(outline: Outline) {
        outline.setPath(path)
    }

    override fun setAlpha(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun setColorFilter(p0: ColorFilter?) {
        TODO("Not yet implemented")
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat")
    )
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}