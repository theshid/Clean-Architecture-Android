package com.example.ecomkt.data_local.db.mapper

import com.example.ecomkt.data_local.db.entity.DbCommunityInformation
import com.example.ecomkt.domain.models.CommunityInformation

interface DbMapper {
    fun mapDomainCommunityInformationToDb(communityInformation: CommunityInformation):DbCommunityInformation

    fun mapDbCommunityInformationToDomain(dbCommunityInformation: DbCommunityInformation): CommunityInformation
}