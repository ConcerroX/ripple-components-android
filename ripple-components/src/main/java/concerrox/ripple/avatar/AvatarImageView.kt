package concerrox.ripple.avatar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import concerrox.ripple.Material
import concerrox.ripple.R
import concerrox.ripple.drawable.LayerDrawableCompat
import concerrox.ripple.internal.ResourcesUtils
import concerrox.ripple.internal.dp

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.avatarImageViewStyle,
    defStyleRes: Int = if (Material.isMaterial3Enabled(context)) {
        R.style.Widget_Ripple_Material2_AvatarImageView_Circle
    } else {
        R.style.Widget_Ripple_Material2_AvatarImageView_Circle
    }
) : ShapeableImageView(context, attrs, defStyleAttr) {

    internal var avatarShapeAppearanceModel: ShapeAppearanceModel? = null
    internal var avatarSize: Int = 48.dp

    internal var statusIndicatorSize = 10.dp
    internal var statusIndicatorShapeAppearanceModel =
        ShapeAppearanceModel.builder().setAllCornerSizes(RelativeCornerSize(0.5f)).build()
    var statusIndicatorDrawable: Drawable? = null
        set(value) {
            field = value
            updateAvatarDrawableInternal()
        }
    internal var statusIndicatorMargin = 2.dp
    internal var statusIndicatorHorizontalOffset = 4.dp
    internal var statusIndicatorVerticalOffset = 4.dp

    var isBorderEnabled = false
//        set(value) {
//            field = value
////            updateAvatarDrawableInternal()
//        }

    private var realImageDrawable: Drawable? = null

    init {
        val attributes = context.obtainStyledAttributes(
            attrs, R.styleable.AvatarImageView, defStyleAttr, defStyleRes
        )
        shapeAppearanceModel = ResourcesUtils.getShapeAppearanceModelResource(
            context, attributes.getResourceId(R.styleable.AvatarImageView_shapeAppearance, 0)
        )
        avatarSize =
            attributes.getDimensionPixelSize(R.styleable.AvatarImageView_android_maxWidth, 0)
        super.setShapeAppearanceModel(ShapeAppearanceModel())
        attributes.recycle()
        elevation = 2f.dp
    }

    override fun setImageDrawable(drawable: Drawable?) {
        realImageDrawable = drawable
        if (drawable == null) {
            super.setImageDrawable(null)
        } else {
            updateAvatarDrawableInternal()
        }
    }

    private fun updateAvatarDrawableInternal() {
        realImageDrawable?.let {
            val avatarDrawableWithStatusIndicator = LayerDrawableCompat(
                arrayOf(
                    AvatarDrawable(this, realImageDrawable!!), statusIndicatorDrawable
                )
            ).apply {
                setLayerSize(0, avatarSize, avatarSize)
                setLayerSize(1, statusIndicatorSize, statusIndicatorSize)
                setLayerGravity(1, Gravity.END or Gravity.BOTTOM)
                setLayerInsetEnd(1, statusIndicatorHorizontalOffset)
                setLayerInsetBottom(1, statusIndicatorVerticalOffset)
            }
            super.setImageDrawable(avatarDrawableWithStatusIndicator)
        }
    }

    override fun setShapeAppearanceModel(shapeAppearanceModel: ShapeAppearanceModel) {
        avatarShapeAppearanceModel = shapeAppearanceModel
    }

}