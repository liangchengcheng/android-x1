package com.hdsx.x1.ui.detail

import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.model.room.RoomHelper

class DetailRepository {

    /**
     * 保存历史记录到本地的数据库
     */
    suspend fun saveReadHistory(article: Article) = RoomHelper.addReadHistory(article)
}