package com.example.ecomkt.app.di

import com.example.ecomkt.data_local.db.dao.CommunityDao
import com.example.ecomkt.data_local.db.repository.EcomLocalRepository
import com.example.ecomkt.data_local.db.repository.IEcomLocalRepository
import com.example.ecomkt.data_remote.api.ApiInterface
import com.example.ecomkt.data_remote.repository.EcomRepository
import com.example.ecomkt.data_remote.repository.IEcomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEcomRepository(api:ApiInterface): IEcomRepository = EcomRepository(api)

    @Provides
    @Singleton
    fun provideEcomLocalRepository(dao: CommunityDao):IEcomLocalRepository = EcomLocalRepository(dao)
}