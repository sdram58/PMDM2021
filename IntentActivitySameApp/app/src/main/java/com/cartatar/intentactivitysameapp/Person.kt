package com.cartatar.intentactivitysameapp

import android.os.Parcel
import android.os.Parcelable

data class Person(val name: String?, val surname: String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),//name
        parcel.readString()//surname, the parameters have to have same order that writeToParcel
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}