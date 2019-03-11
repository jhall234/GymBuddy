package com.csci448.jhallinan.gymbuddy

import android.os.Parcel
import android.os.Parcelable

data class WorkoutCardItem(val title: String, val imageResource: Int): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt())


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(imageResource)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkoutCardItem> {
        override fun createFromParcel(parcel: Parcel): WorkoutCardItem {
            return WorkoutCardItem(parcel)
        }

        override fun newArray(size: Int): Array<WorkoutCardItem?> {
            return arrayOfNulls(size)
        }
    }


}