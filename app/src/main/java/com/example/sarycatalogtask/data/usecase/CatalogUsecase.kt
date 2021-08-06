package com.example.sarycatalogtask.data.usecase

import com.example.sarycatalogtask.domain.response.ApiResponse
import com.example.sarycatalogtask.domain.response.ErrorResponse
import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import com.example.sarycatalogtask.data.repo.CatalogRepository
import javax.inject.Inject

class CatalogUsecase @Inject constructor(
    private val repository: CatalogRepository,
)  {

    suspend fun fetchAllSupportedBanners(

        ): ApiResponse<BannersData> {
        val response = repository.fetchingBanners(
        )
        return when (response?.isSuccessful) {
            true -> {
                ApiResponse.Success(response.data)
            }
            false -> {
                ApiResponse.Failure(response.errorResponse)
            }
            else -> {
                ApiResponse.Failure(ErrorResponse())
            }
        }
    }


    suspend fun fetchAllSupportedCatalogs(

    ): ApiResponse<CatalogsData> {
        val response = repository.fetchingCatalogs(
        )
        return when (response?.isSuccessful) {
            true -> {
                ApiResponse.Success(response.data)
            }
            false -> {
                ApiResponse.Failure(response.errorResponse)
            }
            else -> {
                ApiResponse.Failure(ErrorResponse())
            }
        }
    }


}