package com.example.ecomkt.data_remote.repository

import com.example.ecomkt.data_remote.api.ApiInterface
import com.example.ecomkt.data_remote.mapper.toDomain
import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.PostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


class EcomRepository @Inject constructor(private val api: ApiInterface) : IEcomRepository {

    override suspend fun postCommunityInfo(communityInfo: CommunityInformation): Flow<PostResponse> =
        flow {
            val postResponse = api.sendInformation(
                communityInfo.communityName,
                communityInfo.geographicalDistrict,
                communityInfo.accessibility,
                communityInfo.distanceToECOM,
                communityInfo.connectedToECG,
                communityInfo.licenseDate,
                communityInfo.latitude,
                communityInfo.longitude,
                communityInfo.image
            )

            emit(postResponse.body()!!.toDomain())
        }


}