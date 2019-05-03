package com.csci448.jhallinan.gymbuddy.plans

import android.os.Parcel
import android.os.Parcelable

data class WorkoutViewItem(val title: String, val imageResource: Int): Parcelable {

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

    companion object CREATOR : Parcelable.Creator<WorkoutViewItem> {
        override fun createFromParcel(parcel: Parcel): WorkoutViewItem {
            return WorkoutViewItem(parcel)
        }

        override fun newArray(size: Int): Array<WorkoutViewItem?> {
            return arrayOfNulls(size)
        }
    }


}