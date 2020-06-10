package com.hdsx.x1.ui.history

import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.model.room.RoomHelper

class HistoryRepository {
    /**
     * 从本地的数据库获取全部历史记录
     */
    suspend fun getReadHistory() = RoomHelper.queryAllReadHistory()

    /**
     * 删除某条历史记录
     */
    suspend fun deleteHistory(article: Article) = RoomHelper.deleteReadHistory(article)


}