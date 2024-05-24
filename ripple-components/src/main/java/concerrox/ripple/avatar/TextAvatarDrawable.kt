package concerrox.ripple.avatar

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import com.google.android.material.internal.TextDrawableHelper
import concerrox.ripple.internal.dp


@SuppressLint("RestrictedApi")
class TextAvatarDrawable(private var text: String) : ColorDrawable(),
    TextDrawableHelper.TextDrawableDelegate {

    @SuppressLint("RestrictedApi")
    private val textDrawableHelper = TextDrawableHelper(this)

    init {
        color = 0xff6200ee.toInt()
        textDrawableHelper.textPaint.apply {
            color = 0xffffffff.toInt()
            textSize = 26f.dp
            textAlign = Paint.Align.CENTER
        }
//            MaterialColors.getColor(context, com.google.android.material.R.attr.colorSecondary, 0)
    }

    @SuppressLint("RestrictedApi")
    override fun onTextSizeChange() {

    }

    override fun onStateChange(stateSet: IntArray): Boolean {
        return super.onStateChange(stateSet)
    }

    @SuppressLint("RestrictedApi")
    override fun draw(canvas: Canvas) {
        val textBounds = Rect()
        textDrawableHelper
            .textPaint
            .getTextBounds(text, 0, text.length, textBounds)

        super.draw(canvas)
        canvas.drawText(
            text,
            (bounds.width() / 2).toFloat(),
            (bounds.height() / 2).toFloat() - textBounds.exactCenterY(),
            textDrawableHelper.textPaint
        )
    }

}