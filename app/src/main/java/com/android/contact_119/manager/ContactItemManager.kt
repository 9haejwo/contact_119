package com.android.contact_119.manager

import com.android.contact_119.R
import com.android.contact_119.data.ContactItems

const val SEOUL = "서울"
const val BUSAN = "부산"
const val DAEJEON = "대전"
const val DAEGU = "대구"
const val INCHEON = "인천"
const val GWANGJOO = "광주"
const val ULSAN = "울산"

object ContactItemManager {
    var contactItems = mutableListOf<ContactItems>()

    init {
        addHeader(SEOUL)
        addHeader(BUSAN)
        addHeader(DAEJEON)
        addHeader(DAEGU)
        addHeader(INCHEON)
        addHeader(ULSAN)
        addHeader(GWANGJOO)
        addContent("서울대학교병원 응급의료센터", "02-2072-1182", "서울 종로구 대학로 101", SEOUL, R.drawable.seoul_univ_pic, R.drawable.seoul_univ_logo, )
        addContent("국립중앙의료원 응급의료센터", "02-2260-7114", "서울 중구 을지로 245", SEOUL, R.drawable.national_medi_pic, R.drawable.national_medi_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("서울아산병원 응급의료센터", "02-3010-3333", "서울 송파구 올림픽로43길 88",SEOUL, R.drawable.seoul_asan_pic, R.drawable.seoul_asan_logo)
        addContent("세브란스병원 응급진료센터", "02-2227-7777", "서울 서대문구 연세로 50", SEOUL, R.drawable.sevrance_pic, R.drawable.sevrance_logo)
        addContent("광혜병원 응급실", "051-504-2119", "부산 동래구 충렬대로 96", BUSAN, R.drawable.gwanghye_pic, R.drawable.gwanghye_logo)
        addContent("한일병원 응급실", "02-901-3119", "서울 도봉구 우이천로 308",SEOUL, R.drawable.hanil_pic, R.drawable.hanil_logo)
        addContent("대동병원 지역응급의료센터", "051-550-9390", "부산 동래구 충렬대로 187 대동병원", BUSAN, R.drawable.daedong_pic, R.drawable.daedong_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",BUSAN, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102",BUSAN, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEJEON, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", SEOUL, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", BUSAN,  R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEGU, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEJEON, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEGU, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
        addContent("중앙대학교병원 응급의료센터", "02-6299-1339", "서울 동작구 흑석로 102", DAEGU, R.drawable.chung_ang_univ_pic, R.drawable.chung_ang_univ_logo)
    }

    fun addContent(name: String, phoneNumber: String, address: String,location: String, profileImage: Int? = null, picture: Int? = null) {
        contactItems.add(ContactItems.Contents(name, phoneNumber, address, location, profileImage, picture))
    }

    fun addContent(item: ContactItems.Contents) {
        contactItems.add(item)
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
            if (contactItems.any { it.location == i.location && it is ContactItems.Contents }) {
                temp += i
                temp += contactItems.filter { it is ContactItems.Contents && it.location == i.location }
            }
        }
        return temp
    }
}