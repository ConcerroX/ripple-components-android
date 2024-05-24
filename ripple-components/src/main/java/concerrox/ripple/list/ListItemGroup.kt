package concerrox.ripple.list

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Space
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import concerrox.ripple.Material
import concerrox.ripple.R
import concerrox.ripple.internal.dp2px
import concerrox.ripple.internal.useAndRecycle

class ListItemGroup @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        0
    } else {
        0
    }
) : ViewGroup(context) {

    private val title = " "

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ListItemGroup, defStyleAttr, defStyleRes).useAndRecycle {

        }
    }

    private val itemViews = mutableListOf<View>()

    override fun onLayout(isChanged: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        setMeasuredDimension(0, 0)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        itemViews.add(View(context))
        itemViews.add(0, View(context))
        itemViews.forEachIndexed { i, view ->
            val layoutParams = when (i) {
                0 -> LinearLayoutCompat.LayoutParams(MATCH_PARENT, 8.dp2px(context)).apply {
                    setMargins(16.dp2px(context), 16.dp2px(context), 16.dp2px(context), 0)
                }

                itemViews.size - 1 -> LinearLayoutCompat.LayoutParams(MATCH_PARENT, 8.dp2px(context)).apply {
                    setMargins(16.dp2px(context), 0, 16.dp2px(context), 16.dp2px(context))
                }

                else -> LinearLayoutCompat.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    setMargins(16.dp2px(context), 0, 16.dp2px(context), 0)
                }
            }
            val shapeAppearanceModel = when (i) {
                0 -> ShapeAppearanceModel.builder().setTopLeftCorner(
                    CornerFamily.ROUNDED, AbsoluteCornerSize(4.dp2px(context).toFloat())
                ).setTopRightCorner(
                    CornerFamily.ROUNDED, AbsoluteCornerSize(4.dp2px(context).toFloat())
                ).build()

                itemViews.size - 1 -> ShapeAppearanceModel.builder().setBottomLeftCorner(
                    CornerFamily.ROUNDED, AbsoluteCornerSize(4.dp2px(context).toFloat())
                ).setBottomRightCorner(
                    CornerFamily.ROUNDED, AbsoluteCornerSize(4.dp2px(context).toFloat())
                ).build()

                else -> ShapeAppearanceModel.builder().build()
            }
            view.background = MaterialShapeDrawable(shapeAppearanceModel).apply {
                fillColor = ColorStateList.valueOf(0xffffffff.toInt())
            }
            view.elevation = 2.dp2px(context).toFloat()
            (parent as LinearLayoutCompat).addView(view, layoutParams)
        }
    }

    override fun addView(child: View?) {
        child?.let { itemViews.add(child) }
    }

    override fun addView(child: View?, index: Int) = addView(child)
    override fun addView(child: View?, params: LayoutParams?) = addView(child)
    override fun addView(child: View?, width: Int, height: Int) = addView(child)
    override fun addView(child: View?, index: Int, params: LayoutParams?) = addView(child)

//    private fun stylizeViewAndAddToParent(view: View) {
////
//    }

}