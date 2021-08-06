package com.example.sarycatalogtask.domain.service

import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import retrofit2.Response
import retrofit2.http.*

interface SaryCatalogService {

    @GET("v2.5.1/baskets/76097/banners")
    suspend fun apiGetAllBanners(
    ): Response<BannersData>

    @GET("v2.5.1/baskets/76097/catalog")
    suspend fun apiGetAllCatalog(
    ): Response<CatalogsData>

}
