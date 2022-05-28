package com.example.ecomkt.domain.usecases

import com.example.ecomkt.data_local.db.repository.IEcomLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias DeleteCommunityInfoByIdBaseUseCase = BaseUseCase<Int, Flow<Int>>

class DeleteCommunityInfoByIdUseCase @Inject constructor(
    private val localRepository: IEcomLocalRepository
) : DeleteCommunityInfoByIdBaseUseCase {

    override suspend fun invoke(params: Int) = localRepository.deleteCommunityInformation(params)

}