package com.example.sarycatalogtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sarycatalogtask.data.usecase.CatalogUsecase
import com.example.sarycatalogtask.data.ErrorResponse
import com.example.sarycatalogtask.domain.states.State
import com.example.sarycatalogtask.data.banners.BannersData
import com.example.sarycatalogtask.data.catalog.CatalogsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ViewModelDashboard
@ViewModelInject
constructor(private val usecaseCabinet: CatalogUsecase) : ViewModel() {


    /**
     * Dashboard
     */
    private val _uiStateGetAllBanners = MutableLiveData<State<BannersData>>()
    val uiStateGetAllBanners: LiveData<State<BannersData>>
        get() = _uiStateGetAllBanners

    private val _uiStateGetAllCatalogs = MutableLiveData<State<CatalogsData>>()
    val uiStateGetAllCatalogs: LiveData<State<CatalogsData>>
        get() = _uiStateGetAllCatalogs


    fun getAllBanners(
    ) {
        _uiStateGetAllBanners.postValue(State.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            usecaseCabinet.fetchAllSupportedBanners(

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
            usecaseCabinet.fetchAllSupportedCatalogs(

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