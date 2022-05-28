package com.example.ecomkt.domain.usecases

import com.example.ecomkt.data_remote.repository.IEcomRepository
import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.PostResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias SendInfoBaseUseCase = BaseUseCase<CommunityInformation, Flow<PostResponse>>

class SendCommunityInfoUseCase @Inject constructor(
    private val repository: IEcomRepository
) : SendInfoBaseUseCase {

    override suspend operator fun invoke(params: CommunityInformation) =
        repository.postCommunityInfo(params)
}