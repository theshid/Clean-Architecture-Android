package com.example.ecomkt.data_local.db.repository

import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.Result
import dagger.Provides
import kotlinx.coroutines.flow.Flow

interface IEcomLocalRepository {

    fun getAllCommunityInformation():Flow<List<CommunityInformation>>

    suspend fun deleteCommunityInformation(CommunityId: Int):Flow<Int>

    suspend fun insertCommunityInformation(communityInformation: CommunityInformation):Flow<Result>
}