package com.example.sarycatalogtask.data.repo

import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import com.example.sarycatalogtask.domain.response.ApiResponse
import com.example.sarycatalogtask.domain.service.SaryCatalogService
import com.example.sarycatalogtask.repository.BaseCatalogRepository
import javax.inject.Inject

class CatalogRepository @Inject constructor(private val service: SaryCatalogService) :
    BaseCatalogRepository(service) {

    override suspend fun fetchingBanners(
    ): ApiResponse<BannersData>? {
        return handleRequest { service.apiGetAllBanners() }
    }

    override suspend fun fetchingCatalogs(
    ): ApiResponse<CatalogsData>? {
        return handleRequest { service.apiGetAllCatalog() }
    }


}