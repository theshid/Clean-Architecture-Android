package com.example.ecomkt.domain.usecases

import com.example.ecomkt.data_local.db.repository.IEcomLocalRepository
import com.example.ecomkt.domain.models.CommunityInformation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetAllCommunityInfoBaseUseCase = BaseUseCase<Unit, Flow<List<CommunityInformation>>>

class GetAllCommunityInfoUseCase @Inject constructor(
    private val localRepository: IEcomLocalRepository
) : GetAllCommunityInfoBaseUseCase {

    override suspend fun invoke(params: Unit) = localRepository.getAllCommunityInformation()

}