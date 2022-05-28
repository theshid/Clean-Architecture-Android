package com.example.ecomkt.data_remote.mapper

import com.example.ecomkt.data_remote.models.ApiPostResponse
import com.example.ecomkt.domain.models.PostResponse

class ApiMapperImpl : ApiMapper {

    override fun mapApiPostResponseToDomain(apiPostResponse: ApiPostResponse): PostResponse {
        return with(apiPostResponse) {
            PostResponse(
                id, status, message
            )
        }
    }
}