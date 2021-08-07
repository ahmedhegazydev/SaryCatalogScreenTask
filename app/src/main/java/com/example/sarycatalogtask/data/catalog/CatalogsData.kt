package com.example.sarycatalogtask.data.catalog

import com.example.sarycatalogtask.data.banners.BannerResult

data class CatalogsData(
    val message: String,
    val other: Other,
    val result: List<CatalogResult>,
    val status: Boolean
)
data class Data(
    val cover: String? = "",
    val deep_link: String? = "",
    val filters: List<Filter>? = listOf(),
    val group_id: Int? = 0,
    val image: String? = "",
    val name: String? = "",

){
    companion object{
            fun populateShimmerDataList(): MutableList<Data>{
                return mutableListOf(
                    Data(),
                    Data(),
                    Data(),
                    Data(),
                    Data(),
                )
            }
    }
}

data class Filter(
    val filter_id: Int,
    val name: String
)


data class Other(
    val business_status: BusinessStatus,
    val show_special_order_view: Boolean,
    val uncompleted_profile_settings: UncompletedProfileSettings
)

data class CatalogResult(
    val `data`: List<Data>,
    val data_type: String,
    val id: Int,
    val row_count: Int,
    val show_more_enabled: Boolean,
    val show_title: Boolean,
    val subtitle: String,
    val title: String,
    val ui_type: String
)


data class UncompletedProfileSettings(
    val image: String,
    val is_completed_profile: Boolean,
    val message: String,
    val show_tag: Boolean
)


data class BusinessStatus(
    val id: Int,
    val title: String
)

