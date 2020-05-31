package com.hdsx.x1.model.bean

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Tag(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "article_id")

    var articleId: Long,
    var name: String?,
    var url: String?
) : Parcelable