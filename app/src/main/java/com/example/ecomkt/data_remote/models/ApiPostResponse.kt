package com.example.ecomkt.data_remote.models

import com.squareup.moshi.Json

data class ApiPostResponse(
    @field:Json(name = "ID") val id: Int,
    @field:Json(name = "Status") val status: Int,
    @field:Json(name = "Message") val message: String
) {
}