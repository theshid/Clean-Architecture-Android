package com.example.ecomkt.data_local.db.repository

import com.example.ecomkt.data_local.db.dao.CommunityDao
import com.example.ecomkt.data_local.db.mapper.toDomain
import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EcomLocalRepository @Inject constructor(val dao: CommunityDao) : IEcomLocalRepository {

    override fun getAllCommunityInformation(): Flow<List<CommunityInformation>> = flow {
        val communities = dao.getAll()
        emit(communities.map { it.toDomain() })
    }

    override suspend fun deleteCommunityInformation(communityId: Int):Flow<Int> =flow{
        val rowAffected = dao.deleteById(communityId)
        emit(rowAffected)
    }

    override suspend fun insertCommunityInformation(communityInformation: CommunityInformation): Flow<Result> =
        flow {
            val result = dao.insert(communityInformation)
            emit(result)
        }


}