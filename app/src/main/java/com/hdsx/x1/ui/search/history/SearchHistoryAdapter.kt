package com.hdsx.x1.ui.search.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdsx.R
import kotlinx.android.synthetic.main.item_search_history.view.*

/**
 * 传入上下文对象 和  布局对象
 */
class SearchHistoryAdapter(private var context: Context, private var layoutResId: Int = R.layout.item_search_history) :
    ListAdapter<String, SearchHistoryHolder>(HistoryDiffCallback()) {

    /**
     * 条目点击事件
     */
    var onItemClickListener: ((position: Int) -> Unit)? = null

    /**
     * 条目删除事件
     */
    var onDeleteClickListener: ((position: Int) -> Unit)? = null

    /**
     * 数据源
     */
    var data: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        return SearchHistoryHolder(
            LayoutInflater.from(context).inflate(layoutResId, parent, false)
        )
    }

    /**
     * 绑定数据
     */
    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int) {
        holder.itemView.run {
            tvLabel.text = getItem(position)
            setOnClickListener {
                onItemClickListener?.invoke(holder.adapterPosition)
            }
            ivDelete.setOnClickListener {
                onDeleteClickListener?.invoke(holder.adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int, payloads: MutableList<Any>) {
        onBindViewHolder(holder, position)
    }

    override fun submitList(list: MutableList<String>?) {
        data = if (list.isNullOrEmpty()) {
            mutableListOf()
        } else {
            ArrayList(list)
        }
        super.submitList(data)
    }
}

class SearchHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

/**
 * 回调
 */
class HistoryDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}