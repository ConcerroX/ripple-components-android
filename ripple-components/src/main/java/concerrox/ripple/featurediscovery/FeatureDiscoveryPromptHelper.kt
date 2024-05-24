package concerrox.ripple.featurediscovery

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup

object FeatureDiscoveryPromptHelper {

    private val LOG_TAG = this::class.simpleName

    fun showFromView(view: View) {
        if (view.context is Activity) {
            val decorView = (view.context as Activity).window.decorView as ViewGroup
//                .findViewById<View>(android.R.id.content)
            decorView.addView(FeatureDiscoveryPromptView(view.context).apply {
                anchorView = view
                containerView = decorView
                calculateSizes()
            })
        } else {
            Log.w(LOG_TAG, "You must build a FeatureDiscoveryPromptView from an Activity! ")
        }
    }

}