package com.example.ecomkt.data_local.db.mapper

import com.example.ecomkt.data_local.db.entity.DbCommunityInformation
import com.example.ecomkt.domain.models.CommunityInformation

internal fun CommunityInformation.toEntity(): DbCommunityInformation = DbCommunityInformation(
    communityName = this.communityName,
    geographicalDistrict = this.geographicalDistrict,
    accessibility = this.accessibility,
    distanceToECOM = this.distanceToECOM,
    connectedToECG = this.connectedToECG,
    licenseDate = this.licenseDate,
    latitude = this.latitude,
    longitude = this.longitude,
    image = this.image,
    createdDate = this.createdDate,
    createdBy = this.createdBy,
    updateDate = this.updateDate,
    updateBy = this.updateBy,
    sentServer = this.sentServer
)