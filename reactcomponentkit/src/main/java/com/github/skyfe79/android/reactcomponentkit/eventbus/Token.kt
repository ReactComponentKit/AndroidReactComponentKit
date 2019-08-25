package com.github.skyfe79.android.reactcomponentkit.eventbus

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Token(val token: String = UUID.randomUUID().toString()) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString() ?: UUID.randomUUID().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(token)
    }

    companion object {
        val empty: Token =
            Token("")

        @JvmField
        val CREATOR: Parcelable.Creator<Token> = object : Parcelable.Creator<Token> {
            override fun createFromParcel(source: Parcel): Token = Token(source)
            override fun newArray(size: Int): Array<Token?> = arrayOfNulls(size)
        }
    }
}