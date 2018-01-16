package com.example.jw_numbers.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class NumberDTO(var description: String = "", var number: String = "", var place: String = "", var firebaseId: String = "") : Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeString(description)
        parcel?.writeString(number)
        parcel?.writeString(place)
        parcel?.writeString(firebaseId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NumberDTO> {
        override fun createFromParcel(parcel: Parcel): NumberDTO {
            return NumberDTO(parcel)
        }

        override fun newArray(size: Int): Array<NumberDTO?> {
            return arrayOfNulls(size)
        }
    }

}