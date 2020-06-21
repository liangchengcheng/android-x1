package com.hdsx.x1.ui.opensource

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hdsx.R
import com.hdsx.x1.model.bean.Article
import kotlinx.android.synthetic.main.item_open_source.view.*

class OpenSourceAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_open_source) {
    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.itemView.run {
            tvTitle.text = item.title
            tvLink.text = item.link
        }
    }
}