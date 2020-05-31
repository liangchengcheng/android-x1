package com.hdsx.x1.model.bean

data class Shared(
    val coinInfo: PointRank,
    val shareArticles: Pagination<Article>
)