package com.example.ecomkt.app.mappers

import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.domain.models.CommunityInformation


internal fun CommunityInfoPresentation.toDomain(): CommunityInformation = CommunityInformation(
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
    sentServer
)