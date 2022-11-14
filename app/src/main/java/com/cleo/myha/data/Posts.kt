package com.cleo.myha.data


data class Posts (
    var id: String = "",
//    var author: String,
    var title: String = "",
    var content: String = "",
    var lastUpdatedTime: String? = null ,
    var photo: String? = null,
    var tag: String = ""
)
