package concerrox.ripple.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.PagerAdapter
import kotlin.reflect.KClass

class MainPagerAdapter(context: Context, private val list: List<Pair<String, ViewBinding>>): PagerAdapter() {

    private val views = ArrayList<View>()

    init {
        list.forEach {
            views.add(it.second.root)
        }
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getPageTitle(position: Int): CharSequence {
        return list[position].first
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(views[position])
    }
}