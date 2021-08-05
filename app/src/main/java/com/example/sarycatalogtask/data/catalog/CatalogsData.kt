package com.example.sarycatalogtask.data.catalog

data class CatalogsData(
    val message: String,
    val other: Other,
    val result: List<Result>,
    val status: Boolean
)
data class Data(
    val cover: Any,
    val deep_link: String,
    val filters: List<Filter>,
    val group_id: Int,
    val image: String,
    val name: String
)

data class Filter(
    val filter_id: Int,
    val name: String
)


data class Other(
    val business_status: BusinessStatus,
    val show_special_order_view: Boolean,
    val uncompleted_profile_settings: UncompletedProfileSettings
)

data class Result(
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

