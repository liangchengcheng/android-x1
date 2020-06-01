package com.hdsx.x1.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.hdsx.x1.model.bean.Article
import com.hdsx.x1.model.bean.Tag

/**
 * 读取历史记录bean ， 文章和一堆标签
 */
data class ReadHistory (
    @Embedded
    var article: Article,
    @Relation(parentColumn = "id", entityColumn = "article_id")
    var tags: List<Tag>
)