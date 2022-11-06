package com.cleo.myha.data

import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId

data class Posts(
    var id: String,
    var author: String,
    var title: String,
    var content: String,
//    var lastUpdatedTime: Long ,
    var photo: String
){

    constructor() : this(
        "",
        "",
        "",
        "",
        ""
    )
}
