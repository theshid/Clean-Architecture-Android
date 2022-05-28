package com.example.ecomkt.data_remote.mapper

import com.example.ecomkt.data_remote.models.ApiPostResponse
import com.example.ecomkt.domain.models.PostResponse

interface ApiMapper {
    fun mapApiPostResponseToDomain(apiPostResponse: ApiPostResponse): PostResponse
}