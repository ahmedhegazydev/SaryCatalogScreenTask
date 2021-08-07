package com.example.sarycatalogtask.di.adapter

import com.example.sarycatalogtask.ui.adapters.*
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
    fun providesAdapterBanners(): TopBannersRecyclerAdapter {
        return TopBannersRecyclerAdapter()
    }


    @Singleton
    @Provides
    fun providesAdapterCategories(): CategoriesRecyclerAdapter {
        return CategoriesRecyclerAdapter()
    }


    @Singleton
    @Provides
    fun providesAdapterCatalog(): TopTrendingRecyclerAdapter {
        return TopTrendingRecyclerAdapter()
    }

    @Singleton
    @Provides
    fun providesAdapterCenterBottomBanners(): CenterBottomBannersAdapter {
        return CenterBottomBannersAdapter()
    }

    @Singleton
    @Provides
    fun providesAdapterByBusinessTypes(): BusinessTypesRecyclerAdapter {
        return BusinessTypesRecyclerAdapter()
    }




}