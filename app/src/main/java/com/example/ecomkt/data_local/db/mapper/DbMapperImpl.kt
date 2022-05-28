package com.example.ecomkt.data_local.db.mapper

import com.example.ecomkt.data_local.db.entity.DbCommunityInformation
import com.example.ecomkt.domain.models.CommunityInformation

class DbMapperImpl : DbMapper {


    override fun mapDomainCommunityInformationToDb(communityInformation: CommunityInformation): DbCommunityInformation {
        return with(communityInformation) {
            DbCommunityInformation(
                communityName = communityName,
                geographicalDistrict = geographicalDistrict,
                accessibility = accessibility,
                distanceToECOM = distanceToECOM,
                connectedToECG = connectedToECG,
                licenseDate = licenseDate,
                latitude = latitude,
                longitude = longitude,
                image = image,
                createdBy = createdBy,
                createdDate = createdDate,
                updateBy = updateBy,
                updateDate = updateDate,
                sentServer = sentServer
            )
        }
    }

    override fun mapDbCommunityInformationToDomain(dbCommunityInformation: DbCommunityInformation): CommunityInformation {
        return with(dbCommunityInformation) {
            CommunityInformation(
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
        }
    }
}