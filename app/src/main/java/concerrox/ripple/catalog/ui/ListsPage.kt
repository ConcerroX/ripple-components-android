package concerrox.ripple.catalog.ui

import android.content.Context
import android.widget.Toast
import concerrox.ripple.catalog.databinding.PageListsBinding
import concerrox.ui.ripple.list.ListLayout

class ListsPage(private val context: Context) : Page(context) {

    override val binding = PageListsBinding.inflate(layoutInflater)
    override val name = "Lists"

    override fun onViewCreated() {
        binding.listLayout.addOnItemClickListener(ListLayout.OnListItemClickListener {
            Toast.makeText(context, it.title, Toast.LENGTH_LONG).show()
        })
    }
}