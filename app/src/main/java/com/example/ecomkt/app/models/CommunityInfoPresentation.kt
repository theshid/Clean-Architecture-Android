package com.example.ecomkt.app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommunityInfoPresentation(
    val communityName: String,
    val geographicalDistrict: Int,
    val accessibility: Int,
    val distanceToECOM: Int,
    val connectedToECG: String,
    val licenseDate: String,
    val latitude: Double,
    val longitude: Double,
    val image: String,
    val createdBy: String = "Shid",
    val createdDate: String,
    val updateBy: String,
    val updateDate: String,
    val sentServer: Boolean,
    val id:Int = -1
):Parcelable
