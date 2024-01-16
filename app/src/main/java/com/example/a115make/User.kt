package com.example.a115make

import com.android.contact_119.data.ContactItems
import java.net.URI

data class User(
    var userName: String,
    var phoneNumber: String,
    var bloodType: String,
    var location: String,
    var profileImage: URI? = null,
    var favorite: MutableList<ContactItems.Contents> = mutableListOf()
)