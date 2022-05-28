package com.example.ecomkt.data_local.db.mapper

import com.example.ecomkt.data_local.db.entity.DbCommunityInformation
import com.example.ecomkt.domain.models.CommunityInformation

internal fun DbCommunityInformation.toDomain(): CommunityInformation = CommunityInformation(
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