package com.hdsx.x1.ui.main.discovery

import android.view.LayoutInflater
import android.view.View
import com.hdsx.R
import com.hdsx.x1.model.bean.Frequently
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

class TagAdapter(private val frequentlyList: List<Frequently>) :
    TagAdapter<Frequently>(frequentlyList) {

    override fun getView(parent: FlowLayout?, position: Int, frequently: Frequently?): View {

        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_nav_tag, parent, false)
            .apply {
                tvTag.text = frequentlyList[position].name
            }
    }
}