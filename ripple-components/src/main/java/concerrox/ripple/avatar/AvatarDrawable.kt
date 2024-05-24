package concerrox.ripple.avatar

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.graphics.toRectF
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.ShapeAppearancePathProvider
import concerrox.ripple.drawable.DrawableWrapper
import concerrox.ripple.internal.dp

class AvatarDrawable(
    private val avatarImageView: AvatarImageView, wrappedDrawable: Drawable
) : DrawableWrapper(wrappedDrawable) {

    companion object {
        @SuppressLint("RestrictedApi")
        private val pathProvider = ShapeAppearancePathProvider.getInstance()
    }

    override fun draw(canvas: Canvas) {
        val size = avatarImageView.avatarSize
        val avatarBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val avatarCanvas = Canvas(avatarBitmap)

        val avatarRectF = wrappedDrawable.bounds.toRectF()
        val avatarPath = Path()
        pathProvider.calculatePath(
            avatarImageView.avatarShapeAppearanceModel, 1f, avatarRectF, avatarPath
        )

        var statusIndicatorRectFWithMargin: RectF? = null
        avatarImageView.statusIndicatorDrawable?.let {
            statusIndicatorRectFWithMargin = Rect(
                size - avatarImageView.statusIndicatorSize - avatarImageView.statusIndicatorHorizontalOffset - avatarImageView.statusIndicatorMargin,
                size - avatarImageView.statusIndicatorSize - avatarImageView.statusIndicatorVerticalOffset - avatarImageView.statusIndicatorMargin,
                size - avatarImageView.statusIndicatorHorizontalOffset + avatarImageView.statusIndicatorMargin,
                size - avatarImageView.statusIndicatorHorizontalOffset + avatarImageView.statusIndicatorMargin
            ).toRectF()
            val statusIndicatorPathWithMargin = Path()
            pathProvider.calculatePath(
                avatarImageView.statusIndicatorShapeAppearanceModel,
                1f,
                statusIndicatorRectFWithMargin,
                statusIndicatorPathWithMargin
            )
            avatarPath.op(statusIndicatorPathWithMargin, Path.Op.DIFFERENCE)
        }
        avatarCanvas.clipPath(avatarPath)
        super.draw(avatarCanvas)
        canvas.drawBitmap(avatarBitmap, 0f, 0f, null)

        if (avatarImageView.isBorderEnabled) {
            val borderRectF = RectF(avatarRectF).apply { inset(0.5f.dp, 0.5f.dp) }
            val borderPath = Path()
            pathProvider.calculatePath(
                avatarImageView.avatarShapeAppearanceModel, 1f, borderRectF, borderPath
            )

            avatarImageView.statusIndicatorDrawable?.let {
                val borderStatusIndicatorRectFWithMargin =
                    RectF(statusIndicatorRectFWithMargin).apply {
                        inset((-0.5f).dp, (-0.5f).dp)
                    }
                val borderStatusIndicatorPathWithMargin = Path()
                pathProvider.calculatePath(
                    avatarImageView.statusIndicatorShapeAppearanceModel,
                    1f,
                    borderStatusIndicatorRectFWithMargin,
                    borderStatusIndicatorPathWithMargin
                )
                borderPath.op(borderStatusIndicatorPathWithMargin, Path.Op.DIFFERENCE)
            }

            canvas.drawPath(borderPath, Paint().apply {
                strokeWidth = 1f.dp
                style = Paint.Style.STROKE
                color = MaterialColors.compositeARGBWithAlpha(
                    MaterialColors.getColor(
                        avatarImageView.context,
                        com.google.android.material.R.attr.colorOnSurface,
                        0
                    ), 31
                )
            })
        }
    }
}