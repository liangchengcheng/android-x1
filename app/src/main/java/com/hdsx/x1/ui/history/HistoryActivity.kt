package com.hdsx.x1.ui.history

import com.hdsx.R
import com.hdsx.x1.ui.base.BaseVmActivity

class HistoryActivity : BaseVmActivity<HistoryViewModel>() {
    companion object {
        fun newInstance(): HistoryActivity {
            return HistoryActivity()
        }
    }

    override fun layoutRes() = R.layout.activity_history

    override fun viewModelClass() = HistoryViewModel::class.java
}