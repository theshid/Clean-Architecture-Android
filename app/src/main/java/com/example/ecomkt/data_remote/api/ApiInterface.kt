package com.example.ecomkt.data_remote.api

import com.example.ecomkt.data_remote.AccessToken
import com.example.ecomkt.data_remote.models.ApiPostResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("api/CommunityRegistration/Masters?sMasterType={masterType}")
    suspend fun getData(@Path("masterType") masterType: String)

    @POST("api/token")
    @FormUrlEncoded
    fun getToken(
        @Header("authorization") auth: String,
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("grant_type") pass: String
    ): Call<AccessToken>

    @POST("api/InstructionSheet/SaveCommunityData")
    @FormUrlEncoded
    suspend fun sendInformation(
        @Field("communityName") name: String,
        @Field("geographicalDistrict") district: Int,
        @Field("accessibility") accessibility: Int,
        @Field("distanceToECOM") distance: Int,
        @Field("connectedToEcg") connected: String,
        @Field("licenseDate") date_license: String,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double,
        @Field("photo") photo: String,

        ): Response<ApiPostResponse>
}