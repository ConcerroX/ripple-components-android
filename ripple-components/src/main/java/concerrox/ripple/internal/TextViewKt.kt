package concerrox.ripple.internal

import android.widget.TextView
import androidx.core.widget.TextViewCompat

fun TextView.setTextAppearanceFromAttr(resAttr: Int) {
    TextViewCompat.setTextAppearance(this, ResourcesUtils.getResourceId(this.context, resAttr))
}

fun TextView.setTextAppearanceResource(resId: Int) {
    TextViewCompat.setTextAppearance(this, resId)
}