@file:JvmName("Material")

package concerrox.ui.ripple

import android.content.Context
import android.util.TypedValue

private fun isAttributeEnabled(context: Context, attr: Int) =
    context.theme.resolveAttribute(attr, TypedValue(), true)

@Suppress("PrivateResource")
fun isMaterialThemeEnabled(context: Context): Boolean {
    return isAttributeEnabled(
        context, com.google.android.material.R.attr.isMaterialTheme
    )
}

@Suppress("PrivateResource")
fun isDynamicColorsEnabled(context: Context): Boolean {
    return isAttributeEnabled(
        context, com.google.android.material.R.attr.isMaterial3DynamicColorApplied
    )
}

@Suppress("PrivateResource")
fun isMaterial3Enabled(context: Context): Boolean {
    return isAttributeEnabled(
        context, com.google.android.material.R.attr.isMaterial3Theme
    )
}

fun isMaterial2Enabled(context: Context): Boolean {
    return isMaterialThemeEnabled(context) && !isMaterial3Enabled(context)
}

@Suppress("PrivateResource")
fun isMaterial1Enabled(context: Context): Boolean {
    TODO()
}

