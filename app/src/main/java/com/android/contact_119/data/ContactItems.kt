package com.android.contact_119.data


sealed class ContactItems(val ItemID: Long, open val location: String) {
    data class Header(
        val headerID: Long,
        override val location: String
    ) : ContactItems(headerID, location)

    data class Contents(
        var contentID: Long,
        var itemName: String,
        var phoneNumber: String,
        var address: String,
        override val location: String,
        var picture: Int? = null,
        var thumbnailImage: Int? = null,
        var favorite: Boolean = false,
    ) : ContactItems(contentID, location)
}