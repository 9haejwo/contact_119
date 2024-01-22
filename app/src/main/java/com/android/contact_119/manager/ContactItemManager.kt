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

    private var idKey = 0L

    init {
        addHeader(SEOUL)
        addHeader(BUSAN)
        addHeader(DAEJEON)
        addHeader(DAEGU)
        addHeader(INCHEON)
        addHeader(ULSAN)
        addHeader(GWANGJOO)
        addContent(
            "서울대학교병원 응급의료센터",
            "02-2072-1182",
            "서울 종로구 대학로 101",
            SEOUL,
            R.drawable.seoul_univ_pic,
            R.drawable.seoul_univ_logo,
        )
        addContent(
            "국립중앙의료원 응급의료센터",
            "02-2260-7114",
            "서울 중구 을지로 245",
            SEOUL,
            R.drawable.national_medi_pic,
            R.drawable.national_medi_logo
        )
        addContent(
            "중앙대학교병원 응급의료센터",
            "02-6299-1339",
            "서울 동작구 흑석로 102",
            SEOUL,
            R.drawable.chung_ang_univ_pic,
            R.drawable.chung_ang_univ_logo
        )
        addContent(
            "서울아산병원 응급의료센터",
            "02-3010-3333",
            "서울 송파구 올림픽로43길 88",
            SEOUL,
            R.drawable.seoul_asan_pic,
            R.drawable.seoul_asan_logo
        )
        addContent(
            "세브란스병원 응급진료센터",
            "02-2227-7777",
            "서울 서대문구 연세로 50",
            SEOUL,
            R.drawable.sevrance_pic,
            R.drawable.sevrance_logo
        )
        addContent(
            "광혜병원 응급실",
            "051-504-2119",
            "부산 동래구 충렬대로 96",
            BUSAN,
            R.drawable.gwanghye_pic,
            R.drawable.gwanghye_logo
        )
        addContent(
            "한일병원 응급실",
            "02-901-3119",
            "서울 도봉구 우이천로 308",
            SEOUL,
            R.drawable.hanil_pic,
            R.drawable.hanil_logo
        )
        addContent(
            "대동병원 지역응급의료센터",
            "051-550-9390",
            "부산 동래구 충렬대로 187",
            BUSAN,
            R.drawable.daedong_pic,
            R.drawable.daedong_logo
        )
        addContent(
            "삼육부산병원 응급실",
            "051-600-7575",
            "부산 서구 대티로 170",
            BUSAN,
            R.drawable.samyuk_busan_pic,
            R.drawable.samyuk_busan_logo
        )
        addContent(
            "좋은문화병원 응급실",
            "051-630-0909",
            "부산 동구 범일로 119",
            BUSAN,
            R.drawable.good_culture_pic,
            R.drawable.good_culture_logo
        )
        addContent(
            "충남대학교병원 응급센터",
            "042-280-8129",
            "대전 중구 문화로 282",
            DAEJEON,
            R.drawable.chungnam_univ_pic,
            R.drawable.chungnam_univ_logo
        )
        addContent(
            "을지대학교병원 응급실",
            "042-259-1119",
            "대전 서구 둔산서로 95",
            DAEJEON,
            R.drawable.ulji_univ_pic,
            R.drawable.ulji_univ_logo
        )
        addContent(
            "건양대학교병원 권역응급의료센터",
            "042-600-9119",
            "대전 서구 관저동로 158",
            DAEJEON,
            R.drawable.konyang_univ_pic,
            R.drawable.konyang_univ_logo
        )
        addContent(
            "경북대학교병원 응급실",
            "053-200-5114",
            "대구 중구 동덕로 130",
            DAEGU,
            R.drawable.kyungpook_univ_pic,
            R.drawable.kyungpook_univ_logo
        )
        addContent(
            "나사렛종합병원 응급실",
            "0507-1457-3119",
            "대구 달서구 월배로 97",
            DAEGU,
            R.drawable.nazareth_pic,
            R.drawable.nazareth_logo
        )
        addContent(
            "대구가톨릭대학교병원 응급의료센터",
            "053-650-4194",
            "대구 남구 두류공원로17길 33",
            DAEGU,
            R.drawable.daegu_catholic_univ_pic,
            R.drawable.daegu_catholic_univ_logo
        )
    }

    fun addContent(
        name: String,
        phoneNumber: String,
        address: String,
        location: String,
        profileImage: Int? = null,
        picture: Int? = null
    ) {
        contactItems.add(
            ContactItems.Contents(
                idKey,
                name,
                phoneNumber,
                address,
                location,
                profileImage,
                picture
            )
        )
        idKey++
    }

    private fun addHeader(location: String) {
        contactItems.add(ContactItems.Header(idKey, location))
        idKey++
    }

    fun toggleFavorite(id: Long) {
        val nowItem = contactItems.find { it.ItemID == id } as ContactItems.Contents

        nowItem.favorite = !nowItem.favorite
    }

    fun getById(itemId: Long): ContactItems.Contents {
        return contactItems.find { it.ItemID == itemId } as ContactItems.Contents
    }

    fun sortAllWithHeader(): MutableList<ContactItems> {
        return sortItem(contactItems) { header, item ->
            header.location == item.location
        }
    }

    fun sortFavoriteWithHeader(): MutableList<ContactItems> {
        return sortItem(contactItems) { header, item ->
            header.location == item.location && item.favorite
        }
    }

    private fun sortItem(
        item: MutableList<ContactItems>,
        condition: (ContactItems.Header, ContactItems.Contents) -> Boolean
    ): MutableList<ContactItems> {
        val temp = mutableListOf<ContactItems>()
        val headerList = item.filterHeader().toMutableList()

        for (i in headerList) {
            if (!item.filterContents().any { condition(i, it) }) {
                continue
            }
            temp += i
            item.filterContents().forEach {
                if (condition(i, it)) {
                    temp += it.copy()
                }
            }
        }
        return temp
    }

    private fun MutableList<ContactItems>.filterContents(): MutableList<ContactItems.Contents> {
        return filterIsInstance<ContactItems.Contents>().toMutableList()
    }

    private fun MutableList<ContactItems>.filterHeader(): MutableList<ContactItems.Header> {
        return filterIsInstance<ContactItems.Header>().toMutableList()
    }
}