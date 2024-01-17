package com.android.contact_119.manager

import android.util.Log
import com.android.contact_119.data.ContactItems
import com.android.contact_119.data.User
import com.android.contact_119.nowUser
import java.net.URI

object UserManager {
    var users = mutableListOf(
        User(nowUser, "010-1234-1234", "Z형", "스파르타시 디스이즈길 123")
    )

    fun addUser(name: String, phoneNumber: String, bloodType: String, location: String, profileImage: URI? = null) {
        users.add(User(name, phoneNumber, bloodType, location, profileImage))
    }

    fun getUserByName(name: String): User {
        return users.find { it.userName == name } ?: User("홍길동", "010-1234-1234", "Z형", "스파르타시 디스이즈길 123")
    }

    fun addFavoriteItem(name: String, item: ContactItems) {
        getUserByName(name).favorite.add(item as ContactItems.Contents)
    }
}