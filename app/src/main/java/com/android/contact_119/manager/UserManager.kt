package com.android.contact_119.manager

import com.android.contact_119.data.User
import java.net.URI

object UserManager {
    var users = mutableListOf<User>()

    init {
        addUser("홍길동", "010-1234-1234", "Z형", "스파르타시 디스이즈길 123")
    }

    fun addUser(name: String, phoneNumber: String, bloodType: String, location: String, profileImage: URI? = null) {
        users.add(User(name, phoneNumber, bloodType, location, profileImage))
    }
}