package com.hdsx.x1.ui.main.system.pager

import android.os.Bundle
import com.hdsx.x1.common.ScrollToTop
import com.hdsx.x1.model.bean.Category
import com.hdsx.x1.ui.base.BaseVmFragment

class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>(), ScrollToTop {

    companion object {

        private const val CATEGORY_LIST = "CATEGORY_LIST"

        fun newInstance(categoryList: ArrayList<Category>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGORY_LIST, categoryList)
                }
            }
        }
    }
}