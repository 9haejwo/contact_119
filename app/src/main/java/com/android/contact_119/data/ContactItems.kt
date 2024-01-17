package com.android.contact_119.data


    sealed class ContactItems(open val location: String) {
        data class Header(
            override val location: String
        ) : ContactItems(location)

        data class Contents(
            var itemName: String,
            var phoneNumber: String,
            var address: String,
            override val location: String,
            var picture: Int? = null,
            var thumbnailImage: Int? = null,
        ) : ContactItems(location)
    }