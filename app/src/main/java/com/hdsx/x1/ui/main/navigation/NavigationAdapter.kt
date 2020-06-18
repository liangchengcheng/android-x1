package com.hdsx.x1.ui.main.navigation

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hdsx.R
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.model.bean.Navigation
import kotlinx.android.synthetic.main.item_navigation.view.*

class NavigationAdapter(layoutResId: Int = R.layout.item_navigation) :
    BaseQuickAdapter<Navigation, BaseViewHolder>(layoutResId) {

    var onItemTagClickListener: ((article: Article) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.itemView.run {
            title.text = item.name
            tagFlawLayout.adapter = ItemTagAdapter(item.articles)
            tagFlawLayout.setOnTagClickListener { _, position, _ ->
                onItemTagClickListener?.invoke(item.articles[position])
                true
            }
        }
    }
}