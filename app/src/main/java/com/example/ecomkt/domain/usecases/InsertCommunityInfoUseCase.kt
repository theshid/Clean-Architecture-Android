package com.example.ecomkt.domain.usecases

import com.example.ecomkt.data_local.db.repository.IEcomLocalRepository
import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias InsertCommunityInfoBaseUseCase = BaseUseCase<CommunityInformation, Flow<Result>>

class InsertCommunityInfoUseCase @Inject constructor(
    private val localRepository: IEcomLocalRepository
) : InsertCommunityInfoBaseUseCase {

    override suspend fun invoke(params: CommunityInformation) =
        localRepository.insertCommunityInformation(params)

}