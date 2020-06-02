package com.hdsx.x1.model.store

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hdsx.x1.App
import com.hdsx.x1.util.core.getSpValue
import com.hdsx.x1.util.core.putSpValue

object SearchHistoryStore {
    private const val SP_SEARCH_HISTORY = "sp_search_history"
    private const val KEY_SEARCH_HISTORY = "searchHistory"

    private val mGson by lazy { Gson() }

    /**
     * 保存历史记录
     */
    fun saveSearchHistory(words: String) {
        val history = getSearchHistory()
        if (history.contains(words)) {
            history.remove(words)
        }

        history.add(0, words)
        val listStr = mGson.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, App.instance, KEY_SEARCH_HISTORY, listStr)
    }


    /**
     * 根据主键删除历史记录
     */
    fun deleteSearchHistory(words: String) {
        val history = getSearchHistory()
        history.remove(words)
        val listStr = mGson.toJson(history)
        putSpValue(SP_SEARCH_HISTORY, App.instance, KEY_SEARCH_HISTORY, listStr)
    }

    /**
     * 获取全部的历史记录
     */
    fun getSearchHistory(): MutableList<String> {
        val listStr = getSpValue(SP_SEARCH_HISTORY, App.instance, KEY_SEARCH_HISTORY, "")
        return if (listStr.isEmpty()) {
            mutableListOf()
        } else {
            mGson.fromJson<MutableList<String>>(
                listStr,
                object : TypeToken<MutableList<String>>() {}.type
            )
        }
    }
}