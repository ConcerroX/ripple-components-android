package concerrox.ripple.featurediscovery

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.google.android.material.textview.MaterialTextView
import concerrox.ripple.Material
import concerrox.ripple.drawable.BoundedDrawable
import concerrox.ripple.internal.centerX
import concerrox.ripple.internal.dp
import concerrox.ripple.internal.leftToRight
import concerrox.ripple.internal.pow
import concerrox.ripple.internal.setTextAppearanceFromAttr
import concerrox.ripple.internal.xInWindow
import concerrox.ripple.internal.yInWindow
import kotlin.math.sqrt

internal class FeatureDiscoveryPromptView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        0
    } else {
        0
    }
) : FrameLayout(context, attrs, defStyleAttr) {

    internal lateinit var anchorView: View
    internal lateinit var containerView: View

    private val titleTextView =
        MaterialTextView(context).apply { setTextAppearanceFromAttr(com.google.android.material.R.attr.textAppearanceHeadline6) }
    private val summaryTextView =
        MaterialTextView(context).apply { setTextAppearanceFromAttr(com.google.android.material.R.attr.textAppearanceBody1) }

    private var centerX = 0
    private var centerY = 0
    private var radius = 1000
    var c = 0

    private var fd: FeatureDiscoveryPromptDrawable = FeatureDiscoveryPromptDrawable(this)

    init {
        addView(titleTextView.apply {
            text = "Lorem ipsum dolor"
            setTextColor(0xffffffff.toInt())
        }, MATCH_PARENT, WRAP_CONTENT)
        addView(summaryTextView.apply {
            text =
                "The prompt may be modified to accommodate different screen locations and display sizes."
            setTextColor(0xeeffffff.toInt())
        }, MATCH_PARENT, WRAP_CONTENT)

        background = BoundedDrawable(fd)
        elevation = 4f.dp
        clipToOutline = true
//        outlineProvider = object :ViewOutlineProvider() {
//            override fun getOutline(p0: View?, p1: Outline?) {
//                if (p1 != null) {
//                    (background as BoundedDrawable).getOutline(p1)
//                }
//            }
//        }
    }

    fun calculateSizes() {
        centerX = anchorView.xInWindow + (anchorView.right - anchorView.left) / 2
        centerY = anchorView.yInWindow + (anchorView.bottom - anchorView.top) / 2

        c = (anchorView.leftToRight / 2f + 16f.dp).coerceAtLeast(44f.dp).toInt()
//        println(centerX)
        (background as BoundedDrawable).drawableBounds =
            Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        (titleTextView.layoutParams as LayoutParams).apply {
            setMargins(40.dp, centerY + c + 20.dp, 40.dp, 0)
        }
        (summaryTextView.layoutParams as LayoutParams).apply {
            setMargins(40.dp, centerY + c + 48.dp, 40.dp, 0)
        }

        fd.apply {
            innerCircleCenterX = this@FeatureDiscoveryPromptView.centerX.toFloat()
            centerY = this@FeatureDiscoveryPromptView.centerY.toFloat()
            radius = this@FeatureDiscoveryPromptView.radius.toFloat()

            summaryTextView.post {
                tapTargetCenter = PointF(
                    this@FeatureDiscoveryPromptView.centerX.toFloat(),
                    this@FeatureDiscoveryPromptView.centerY.toFloat()
                )
                tapTargetRadius = (anchorView.leftToRight / 2f + 16f.dp).coerceAtLeast(44f.dp)

                val centerX = containerView.centerX.toFloat()

                val l = summaryTextView.bottom.toFloat() - tapTargetCenter.y
                val b = tapTargetCenter.x - centerX
                val c = containerView.right - 40f.dp - centerX
                val tapTargetRadius = tapTargetRadius + 20.dp
                val bSquared = b.pow(2)
                val cSquared = c.pow(2)
                val lSquared = l.pow(2)
                val tapTargetRadiusSquared = tapTargetRadius.pow(2)
                val a1 =
                    (1 / (2 * (lSquared - tapTargetRadiusSquared))) * ((-bSquared * l) + (cSquared * l) + l.pow(
                        3
                    ) - (tapTargetRadius.pow(2) * l) - (tapTargetRadius * sqrt(
                        b.pow(4) - (2 * bSquared * cSquared) + (2 * bSquared * l.pow(2)) - (2 * tapTargetRadiusSquared * bSquared) + c.pow(
                            4
                        ) + (2 * cSquared * lSquared) - (2 * tapTargetRadiusSquared * cSquared) + l.pow(
                            4
                        ) - (2 * tapTargetRadiusSquared * lSquared) + tapTargetRadius.pow(4)
                    )))
                val a2 =
                    (1 / (2 * (lSquared - tapTargetRadiusSquared))) * ((-bSquared * l) + (cSquared * l) + l.pow(
                        3
                    ) - (tapTargetRadius.pow(2) * l) + (tapTargetRadius * sqrt(
                        b.pow(4) - (2 * bSquared * cSquared) + (2 * bSquared * l.pow(2)) - (2 * tapTargetRadiusSquared * bSquared) + c.pow(
                            4
                        ) + (2 * cSquared * lSquared) - (2 * tapTargetRadiusSquared * cSquared) + l.pow(
                            4
                        ) - (2 * tapTargetRadiusSquared * lSquared) + tapTargetRadius.pow(
                            4
                        )
                    )))
                val a = if (a1 > l) a2 else a1
                promptBackgroundCenter = PointF(centerX, tapTargetCenter.y + a)
                promptBackgroundRadius = sqrt((c pow 2) + ((l - a) pow 2)) + 40.dp
                show()
            }
        }
//        start()
    }

    fun start() {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                val i = it.animatedValue as Float
                fd.apply {
//                    radius = this@FeatureDiscoveryPromptView.radius.toFloat() * i
                    invalidate()
                    this@FeatureDiscoveryPromptView.invalidateOutline()
                }
            }
            duration = 375
            start()
        }
    }

}