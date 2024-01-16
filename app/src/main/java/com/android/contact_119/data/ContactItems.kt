package com.android.contact_119.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ContactItems : Parcelable {
    data class Header(
        val location: String
    ) : ContactItems()

    data class Contents(
        var itemName: String,
        var phoneNumber: String,
        var address: String,
        val location: String,
        var picture: Int,
        var thumbnailImage: Int? = null,
    ) : ContactItems()
}
