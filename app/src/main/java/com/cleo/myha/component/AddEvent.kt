package com.cleo.myha.component

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.applandeo.materialcalendarview.EventDay
import java.util.*


//class MyEventDay : EventDay, Parcelable {
//
//    var note: String?
//        private set
//
//    constructor(day: Calendar?, imageResource: Int, note: String?) : super(day, imageResource) {
//        this.note = note
//    }
//
//    private constructor(`in`: Parcel) : super(
//        `in`.readSerializable() as Calendar?,
//        `in`.readInt()
//    ) {
//        note = `in`.readString()
//    }
//
//    override fun writeToParcel(parcel: Parcel, i: Int) {
//        parcel.writeSerializable(calendar)
////        parcel.writeInt(getImageResource())
//        parcel.writeString(note)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object {
//        val creator: Creator<MyEventDay?> = object : Creator<MyEventDay?> {
//            override fun createFromParcel(`in`: Parcel): MyEventDay {
//                return MyEventDay(`in`)
//            }
//
//            override fun newArray(size: Int): Array<MyEventDay?> {
//                return arrayOfNulls(size)
//            }
//        }
//    }
//
//    companion object CREATOR : Creator<MyEventDay> {
//        override fun createFromParcel(parcel: Parcel): MyEventDay {
//            return MyEventDay(parcel)
//        }
//
//        override fun newArray(size: Int): Array<MyEventDay?> {
//            return arrayOfNulls(size)
//        }
//    }
//}