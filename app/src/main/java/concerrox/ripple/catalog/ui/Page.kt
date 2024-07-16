package concerrox.ripple.catalog.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

abstract class Page(context: Context) {
    internal val layoutInflater = LayoutInflater.from(context)
    internal abstract val binding: ViewBinding
    internal abstract val name: String
    internal abstract fun onViewCreated()
}