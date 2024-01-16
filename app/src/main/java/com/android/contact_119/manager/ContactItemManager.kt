package com.android.contact_119.manager

import com.android.contact_119.R
import com.android.contact_119.data.ContactItems

object ContactItemManager {
    var contactItems = mutableListOf<ContactItems.Contents>()

    init {
        addItem("서울대학교병원 응급의료센터", "02-2072-1182", "서울 종로구 대학로 101", R.drawable.seoul_univ_pic, R.drawable.seoul_univ_logo, )
        addItem("국립중앙의료원 응급의료센터", "02-2260-7114", "서울 중구 을지로 245", R.drawable.national_medi_pic, R.drawable.national_medi_logo)
        addItem("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
    }

    fun addItem(name: String, phoneNumber: String, address: String, profileImage: Int, picture: Int? = null) {
        contactItems.add(ContactItems.Contents(name, phoneNumber, address, profileImage, picture))
    }

    fun getAllItems(): MutableList<ContactItems.Contents> {
        return contactItems
    }
}