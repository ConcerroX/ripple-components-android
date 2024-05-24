package concerrox.ripple.internal

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import java.security.AccessController.getContext

internal val View.activity: Activity?
    get() = run {
        var context = this.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }