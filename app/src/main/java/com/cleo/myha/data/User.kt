package com.cleo.myha.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var photo: String = "",
    var email: String = "",
    var blockLists: @RawValue List<String> = emptyList()
) : Parcelable

