package com.hdsx.x1.model.room

import androidx.room.Room
import com.hdsx.x1.App
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.model.bean.Tag

object RoomHelper {

    private val appDatabase by lazy {
        Room.databaseBuilder(App.instance, AppDatabase::class.java, "database_wanandroid").build()
    }

    private val readHistoryDao by lazy { appDatabase.readHistoryDao() }

    /**
     * 查询全部历史记录
     */
    suspend fun queryAllReadHistory() = readHistoryDao.queryAll()
        .map { it.article.apply { tags = it.tags } }.reversed()

    /**
     * 插入阅读历史记录
     */
    suspend fun addReadHistory(article: Article) {
        // 查询原来的删除
        readHistoryDao.queryArticle(article.id)?.let {
            readHistoryDao.deleteArticle(it)
        }

        // 插入现在的
        readHistoryDao.insert(article.apply { primaryKeyId = 0 })

        // 插入标签
        article.tags.forEach {
            readHistoryDao.insertArticleTag(
                Tag(id = 0, articleId = article.id.toLong(), name = it.name, url = it.url)
            )
        }
    }

    /**
     * 删除历史记录
     */
    suspend fun deleteReadHistory(article: Article) = readHistoryDao.deleteArticle(article)
}