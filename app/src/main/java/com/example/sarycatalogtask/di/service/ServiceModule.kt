package com.example.sarycatalogtask.di.service

import com.example.sarycatalogtask.domain.service.SaryCatalogService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module()
@InstallIn(ApplicationComponent::class)
object ServiceModule {


    @Provides
    @Singleton
    fun provideSaryCatalogService(retrofit: Retrofit) : SaryCatalogService {
        return retrofit.create(SaryCatalogService::class.java)
    }

}