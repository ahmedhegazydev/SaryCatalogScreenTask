package com.example.sarycatalogtask.data.banners

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class BannersData(
    val result: List<BannerResult>,
    val status: Boolean
){
    companion object{
        fun populateDShimmerDataList(): MutableList<BannerResult>{
            return mutableListOf(
                BannerResult(),
                BannerResult(),
                BannerResult(),
                BannerResult(),
                BannerResult(),
                BannerResult(),
            )
        }
    }
}

@Parcelize
data class BannerResult(
    val branches: List<Int>? = listOf(),
    val button_text: String? = "",
//    val cities: List<Any>?,
    val created_at: String?= "",
    val description: String?= "",
    val expiry_date: String?= "",
    val expiry_status: Boolean? = false,
    val id: Int? = 0,
    val image: String?= "",
    val is_available: Boolean?= false,
    val level: String?= "",
    val link: String?= "",
    val photo: String?= "",
    val priority: Int?= 0,
    val promo_code: String?= "",
    val start_date: String?= "",
    val title: String= "",
) : Parcelable