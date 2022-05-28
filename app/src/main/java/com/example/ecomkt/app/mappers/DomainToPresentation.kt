package com.example.ecomkt.app.mappers

import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.models.PostResponsePresentation
import com.example.ecomkt.domain.models.CommunityInformation
import com.example.ecomkt.domain.models.PostResponse

internal fun PostResponse.toPresentation() = PostResponsePresentation(this.status, this.message)

internal fun CommunityInformation.toPresentation() = CommunityInfoPresentation(
    communityName,
    geographicalDistrict,
    accessibility,
    distanceToECOM,
    connectedToECG,
    licenseDate,
    latitude,
    longitude,
    image,
    createdBy,
    createdDate,
    updateBy,
    updateDate,
    sentServer,
    id
)