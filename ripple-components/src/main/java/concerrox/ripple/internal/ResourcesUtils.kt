package concerrox.ripple.internal

import android.content.Context
import android.util.TypedValue
import com.google.android.material.shape.ShapeAppearanceModel

object ResourcesUtils {
    fun getResourceId(context: Context, defResAttr: Int): Int {
        val themedValue = TypedValue()
        context.theme.resolveAttribute(defResAttr, themedValue, true)
        return themedValue.resourceId
    }

    fun getShapeAppearanceModelFromAttr(context: Context, defResAttr: Int): ShapeAppearanceModel {
        return ShapeAppearanceModel.builder(
            context, getResourceId(context, defResAttr), 0
        ).build()
    }

    fun getShapeAppearanceModelResource(context: Context, defStyleRes: Int): ShapeAppearanceModel {
        return ShapeAppearanceModel.builder(
            context, defStyleRes, 0
        ).build()
    }
}