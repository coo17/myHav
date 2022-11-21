package com.cleo.myha.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue



@Parcelize
data class Posts (
    var id: String = "",
//    var author: String,
    var title: String = "",
    var content: String = "",
    var lastUpdatedTime: String? = null ,
    var photo: String? = null,
    var tag: String = "",
    var comments: @RawValue List<CommentsInfo> ? = null
): Parcelable


data class CommentsInfo(
    var senderId: String ="",
    var content: String ="",
    var createdTime: String ="",
)