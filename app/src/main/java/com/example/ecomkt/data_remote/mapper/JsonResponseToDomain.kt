package com.example.ecomkt.data_remote.mapper

import com.example.ecomkt.data_remote.models.ApiPostResponse
import com.example.ecomkt.domain.models.PostResponse

internal fun ApiPostResponse.toDomain(): PostResponse {
    return PostResponse(this.id,this.status,this.message)
}