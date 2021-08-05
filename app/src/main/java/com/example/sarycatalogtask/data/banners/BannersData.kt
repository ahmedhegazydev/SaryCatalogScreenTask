package com.example.sarycatalogtask.data.banners

data class BannersData(
    val result: List<Result>,
    val status: Boolean
)

data class Result(
    val branches: List<Int>,
    val button_text: String,
    val cities: List<Any>,
    val created_at: String,
    val description: String,
    val expiry_date: String,
    val expiry_status: Boolean,
    val id: Int,
    val image: String,
    val is_available: Boolean,
    val level: String,
    val link: String,
    val photo: String,
    val priority: Int,
    val promo_code: Any,
    val start_date: String,
    val title: String
)