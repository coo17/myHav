package com.cleo.myha.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var photo: String = "",
    var email: String = "",
    var blockLists: @RawValue List<String> = emptyList()
): Parcelable

//data class BlockInfo(
//    var blockEmail: String ="",
//    var id: String ="",
//    var content: String ="",
//    var userName: String = ""
//)