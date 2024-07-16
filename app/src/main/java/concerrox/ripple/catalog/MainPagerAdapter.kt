package concerrox.ripple.catalog

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import concerrox.ripple.catalog.ui.Page

class MainPagerAdapter(private val list: List<Page>): PagerAdapter() {

    private val views = ArrayList<View>()

    init {
        list.forEach {
            views.add(it.binding.root)
            it.onViewCreated()
        }
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getPageTitle(position: Int): CharSequence {
        return list[position].name
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(views[position])
    }
}