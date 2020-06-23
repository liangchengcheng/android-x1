package com.hdsx.x1.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide.init
import com.hdsx.R
import com.hdsx.x1.ext.hideSoftInput
import com.hdsx.x1.ui.base.BaseActivity
import com.hdsx.x1.ui.search.history.SearchHistoryFragment
import com.hdsx.x1.ui.search.result.SearchResultFragment
import com.hdsx.x1.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity  : BaseActivity() {

    override fun layoutRes() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        // 初始化fragment
        val historyFragment = SearchHistoryFragment.newInstance()
        val resultFragment = SearchResultFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, historyFragment)
            .add(R.id.flContainer, resultFragment)
            .show(historyFragment)
            .hide(resultFragment)
            .commit()

        ivBack.setOnClickListener {
            if (resultFragment.isVisible) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(resultFragment)
                    .commit()
            } else {
                ActivityManager.finish(SearchActivity::class.java)
            }
        }
        ivDone.setOnClickListener {
            val searchWords = acetInput.text.toString()
            if (searchWords.isEmpty()) return@setOnClickListener
            it.hideSoftInput()
            historyFragment.addSearchHistory(searchWords)
            resultFragment.doSearch(searchWords)
            supportFragmentManager
                .beginTransaction()
                .show(resultFragment)
                .commit()
        }
        acetInput.run {
            addTextChangedListener(afterTextChanged = {
                ivClearSearch.isGone = it.isNullOrEmpty()
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ivDone.performClick()
                    true
                } else {
                    false
                }
            }
        }
        ivClearSearch.setOnClickListener { acetInput.setText("") }
    }

    fun fillSearchInput(keywords: String) {
        acetInput.setText(keywords)
        acetInput.setSelection(keywords.length)
    }

    override fun onBackPressed() {
        ivBack.performClick()
    }
}