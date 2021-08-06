package com.example.sarycatalogtask.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sarycatalogtask.data.usecase.CatalogUsecase
import com.example.sarycatalogtask.domain.response.ErrorResponse
import com.example.sarycatalogtask.domain.states.State
import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CatalogsViewModel
@ViewModelInject
constructor(private val usecaseCatalog: CatalogUsecase) : ViewModel() {

    private val _uiStateGetAllBanners = MutableLiveData<State<BannersData>>()
    val uiStateGetAllBanners: LiveData<State<BannersData>>
        get() = _uiStateGetAllBanners

    private val _uiStateGetAllCatalogs = MutableLiveData<State<CatalogsData>>()
    val uiStateGetAllCatalogs: LiveData<State<CatalogsData>>
        get() = _uiStateGetAllCatalogs

    init{

    }

    fun getAllBanners(
    ) {
        _uiStateGetAllBanners.postValue(State.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            usecaseCatalog.fetchAllSupportedBanners(

            )
                .let { response ->
                    when (response.isSuccessful) {
                        true -> {
                            if (response.data is BannersData) {
                                withContext(Dispatchers.Main) {
                                    _uiStateGetAllBanners.postValue(
                                        State.Success(
                                            response.data
                                        )
                                    )
                                }
                            }
                        }
                        false -> {
                            withContext(Dispatchers.Main) {
                                response.errorResponse?.let {
                                    _uiStateGetAllBanners.postValue(
                                        State.Error(
                                            ErrorResponse(
                                                code = it.code,
                                                message = it.message,
                                                it.errors
                                            ), it.message
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    fun getAllCatalogs(
    ) {
        _uiStateGetAllCatalogs.postValue(State.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            usecaseCatalog.fetchAllSupportedCatalogs(

            )
                .let { response ->
                    when (response.isSuccessful) {
                        true -> {
                            if (response.data is CatalogsData) {
                                withContext(Dispatchers.Main) {
                                    _uiStateGetAllCatalogs.postValue(
                                        State.Success(
                                            response.data
                                        )
                                    )
                                }
                            }
                        }
                        false -> {
                            withContext(Dispatchers.Main) {
                                response.errorResponse?.let {
                                    _uiStateGetAllCatalogs.postValue(
                                        State.Error(
                                            ErrorResponse(
                                                code = it.code,
                                                message = it.message,
                                                it.errors
                                            ), it.message
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }


}