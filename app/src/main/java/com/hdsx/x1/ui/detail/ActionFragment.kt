package com.hdsx.x1.ui.detail

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.ui.detail.DetailActivity.Companion.PARAM_ARTICLE

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



    fun show(manager: FragmentManager) {
        if (!this.isAdded) {
            super.show(manager, "ActionFragment")
        }
    }
}