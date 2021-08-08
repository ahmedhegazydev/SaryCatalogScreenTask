package com.example.sarycatalogtask.repository

import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import com.example.sarycatalogtask.domain.response.ApiResponse
import com.example.sarycatalogtask.domain.service.SaryCatalogService
import com.example.sarycatalogtask.repository.base.IRepository


abstract class BaseCatalogRepository : IRepository {
    abstract suspend fun fetchingBanners(): ApiResponse<BannersData>?
    abstract suspend fun fetchingCatalogs(): ApiResponse<CatalogsData>?
}