package com.hdsx.x1.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hdsx.R
import com.hdsx.x1.ext.copyTextIntoClipboard
import com.hdsx.x1.ext.openInExplorer
import com.hdsx.x1.ext.showToast
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.hdsx.x1.util.share
import kotlinx.android.synthetic.main.fragment_detail_acitons.*

class ActionFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance(article: Article): ActionFragment {
            return ActionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_ARTICLE, article)
                }
            }
        }
    }

    private var behavior: BottomSheetBehavior<View>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_acitons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val article = getParcelable<Article>(PARAM_ARTICLE) ?: return@run
            llCollect.visibility = if (article.id != 0) View.VISIBLE else View.GONE
            ivCollect.isSelected = article.collect
            tvCollect.text =
                getString(if (article.collect) R.string.cancel_collect else R.string.add_collect)

            llCollect.setOnClickListener{
                val detailActivity = (activity as? DetailActivity)
                    ?: return@setOnClickListener
                // 如果是详情页面的话，检查登录的状态
                // 登录的话就改变收藏的状态
                // 否则的话直接关闭
                // as?
                if (detailActivity.checkLogin()) {
                    ivCollect.isSelected = !article.collect
                    detailActivity.changeCollect()
                    behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                } else {
                    view.postDelayed({ dismiss() }, 300)
                }
            }

            // 分享
            llShare.setOnClickListener {
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
                share(activity!!, content = article.title + article.link)
            }

            // 打开链接
            llExplorer.setOnClickListener {
                openInExplorer(article.link)
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }

            // 复制粘贴
            llCopy.setOnClickListener {
                context?.copyTextIntoClipboard(article.link, article.title)
                context?.showToast(R.string.copy_success)
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }

            // 刷新页面
            llRefresh.setOnClickListener {
                (activity as? DetailActivity)?.refreshPage()
                behavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet: View = (dialog as BottomSheetDialog).delegate
            .findViewById(com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }


    fun show(manager: FragmentManager) {
        if (!this.isAdded) {
            super.show(manager, "ActionFragment")
        }
    }
}