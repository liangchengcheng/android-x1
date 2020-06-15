package com.hdsx.x1.ui.main.home

import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hdsx.R
import com.hdsx.x1.ext.htmlToSpanned
import com.hdsx.x1.ext.toIntPx
import com.hdsx.x1.model.bean.Category
import kotlinx.android.synthetic.main.item_category_sub.view.*


class CategoryAdapter(layoutResId: Int = R.layout.item_category_sub) :
    BaseQuickAdapter<Category, BaseViewHolder>(layoutResId) {

    private var checkedPosition = 0
    var onCheckedListener: ((position: Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.itemView.run {
            ctvCategory.text = item.name.htmlToSpanned()
            ctvCategory.isChecked = checkedPosition == helper.adapterPosition
            setOnClickListener {
                val position = helper.adapterPosition
                check(position)
                onCheckedListener?.invoke(position)
            }
            updateLayoutParams<MarginLayoutParams> {
                marginStart = if (helper.adapterPosition == 0) 8f.toIntPx() else 0f.toIntPx()
            }
        }
    }

    fun check(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }

}