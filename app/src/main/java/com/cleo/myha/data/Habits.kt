package com.cleo.myha.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Habits(
    var id: String = "",
    var ownerId: String = "",
    var members: @RawValue List<String> ? = null,
    var category: String = "",
    var task: String = "",
    var repeatedDays: List<Boolean> = emptyList(),
    var duration: String = "",
    var reminder: Long = -1L,
    var timer: String = "",
    var createdTime: Long = -1L,
    var startedDate: Long = -1L,
    var endDate: Long = -1L,
    var mode: Int = 0
) : Parcelable
