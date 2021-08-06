package com.example.sarycatalogtask.di.adapter

import com.example.sarycatalogtask.ui.adapters.BannersRecyclerAdapter
import com.example.sarycatalogtask.ui.adapters.CatalogRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module()
@InstallIn(ApplicationComponent::class)
object AdaptersRecyclerModule {


    @Singleton
    @Provides
    fun providesAdapterBanners(): BannersRecyclerAdapter {
        return BannersRecyclerAdapter()
    }


    @Singleton
    @Provides
    fun providesAdapterCatalog(): CatalogRecyclerAdapter {
        return CatalogRecyclerAdapter()
    }


}