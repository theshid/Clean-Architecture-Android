package com.example.ecomkt.data_remote

import com.squareup.moshi.Json

data class AccessToken(
    @field:Json(name = "access_token")
    val token: String,
    @field:Json(name = "token_type")
    val tokenType: String,
    @field:Json(name = "expires_in")
    val expires: Int
)
