package com.android.contact_119.data

sealed class ContactItems {
    data class Title(
        val location: String
    ) : ContactItems()

    data class Contents(
        var itemName: String,
        var phoneNumber: String,
        var address: String,
        var picture: Int,
        var profileImage: Int? = null,
    ) : ContactItems()
}
