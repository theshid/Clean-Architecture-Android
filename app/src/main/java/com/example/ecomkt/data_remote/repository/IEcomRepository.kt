package com.example.ecomkt.data_remote.repository

import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.PostResponse
import kotlinx.coroutines.flow.Flow

interface IEcomRepository {

    suspend fun postCommunityInfo(communityInfo: CommunityInformation):Flow<PostResponse>
}