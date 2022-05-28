package com.example.ecomkt.domain.models

data class CommunityInformation(
    val communityName: String,
    val geographicalDistrict: Int,
    val accessibility: Int,
    val distanceToECOM: Int,
    val connectedToECG: String,
    val licenseDate: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,
    val createdBy: String,
    val createdDate: String,
    val updateBy: String,
    val updateDate: String,
    val sentServer: Boolean,
    val id:Int = -1
)
