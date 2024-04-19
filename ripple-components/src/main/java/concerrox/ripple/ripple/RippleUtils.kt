package concerrox.ripple.ripple

import android.content.Context
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources

object RippleUtils {
    fun createRippleDrawable(context: Context): RippleDrawable {
        val themedValue = TypedValue()
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground, themedValue, true
        )
        return AppCompatResources.getDrawable(
            context, themedValue.resourceId
        )?.mutate() as RippleDrawable
    }
}