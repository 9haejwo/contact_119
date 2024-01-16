package com.android.contact_119.manager

import com.android.contact_119.R
import com.android.contact_119.data.ContactItems

const val SEOUL = "서울"
const val BUSAN = "부산"
const val DAEJEON = "대전"
const val DAEGU = "대구"

object ContactItemManager {
    var contactItems = mutableListOf<ContactItems>()

    init {
        addHeader(SEOUL)
        addContent("서울대학교병원 응급의료센터", "02-2072-1182", "서울 종로구 대학로 101", SEOUL, R.drawable.seoul_univ_pic, R.drawable.seoul_univ_logo, )
        addContent("국립중앙의료원 응급의료센터", "02-2260-7114", "서울 중구 을지로 245", SEOUL, R.drawable.national_medi_pic, R.drawable.national_medi_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", BUSAN, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",BUSAN, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",BUSAN, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEJEON, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addHeader(BUSAN)
        addHeader(DAEJEON)
        addHeader(DAEGU)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", BUSAN,  R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEGU, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEJEON, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEGU, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEGU, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
    }

    fun addContent(name: String, phoneNumber: String, address: String,location: String, profileImage: Int, picture: Int? = null) {
        contactItems.add(ContactItems.Contents(name, phoneNumber, address, location, profileImage, picture))
    }

    fun addHeader(location: String) {
        contactItems.add(ContactItems.Header(location))
    }

    fun getAllItems(): MutableList<ContactItems> {
        return contactItems
    }

    fun sortWithHeader(): MutableList<ContactItems> {
        val temp = mutableListOf<ContactItems>()
        val headerList = contactItems.filter { it is ContactItems.Header }.toMutableList()

        for (i in headerList) {
            val header = i as ContactItems.Header
            temp += i
            temp += contactItems.filter { it is ContactItems.Contents && it.location == header.location }
        }

        return temp
    }
}