package com.hdsx.x1.ui.main.discovery

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hdsx.R
import com.hdsx.x1.model.bean.HotWord
import kotlinx.android.synthetic.main.item_hot_word.view.*

class HotWordsAdapter(layouResId: Int = R.layout.item_hot_word) :
    BaseQuickAdapter<HotWord, BaseViewHolder>(layouResId) {
    override fun convert(helper: BaseViewHolder, item: HotWord) {
        helper.itemView.tvName.text = item.name
        helper.itemView.run {
            tvName.text = item.name
        }
    }

}