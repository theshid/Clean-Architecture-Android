package com.example.ecomkt.app.di

import com.example.ecomkt.data_local.db.repository.IEcomLocalRepository
import com.example.ecomkt.data_remote.repository.IEcomRepository
import com.example.ecomkt.domain.usecases.DeleteCommunityInfoByIdUseCase
import com.example.ecomkt.domain.usecases.GetAllCommunityInfoUseCase
import com.example.ecomkt.domain.usecases.InsertCommunityInfoUseCase
import com.example.ecomkt.domain.usecases.SendCommunityInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSendInfoUseCase(iEcomRepository: IEcomRepository): SendCommunityInfoUseCase =
        SendCommunityInfoUseCase(iEcomRepository)

    @Provides
    fun provideInsertCommunityInfoUseCase(localRepository: IEcomLocalRepository): InsertCommunityInfoUseCase =
        InsertCommunityInfoUseCase(localRepository)

    @Provides
    fun provideGetAllCommunityInfoUseCase(localRepository: IEcomLocalRepository): GetAllCommunityInfoUseCase =
        GetAllCommunityInfoUseCase(localRepository)

    @Provides
    fun provideDeleteCommunityInfo(localRepository: IEcomLocalRepository): DeleteCommunityInfoByIdUseCase =
        DeleteCommunityInfoByIdUseCase(localRepository)
}