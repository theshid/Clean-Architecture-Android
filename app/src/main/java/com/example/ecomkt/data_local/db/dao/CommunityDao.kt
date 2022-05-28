package com.example.ecomkt.data_local.db.dao

import androidx.room.*
import com.example.ecomkt.data_local.db.entity.DbCommunityInformation
import com.example.ecomkt.data_local.db.mapper.toEntity
import com.example.ecomkt.domain.models.CommunityInformation

@Dao
interface CommunityDao {

    @Query("SELECT * FROM community_information")
    suspend fun getAll(): List<DbCommunityInformation>

    @Query("SELECT * FROM community_information WHERE id = :id")
    suspend fun getCommunity(id: Int): DbCommunityInformation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunityInfo(information: DbCommunityInformation)

    @Transaction
    suspend fun insert(communityInformation: CommunityInformation): com.example.ecomkt.domain.models.Result {
        insertCommunityInfo(communityInformation.toEntity())
        return com.example.ecomkt.domain.models.Result.SUCCESS
    }

    /* @Delete
     suspend fun deleteCommunityInfo(information: DbCommunityInformation)*/

    @Query("DELETE FROM community_information WHERE id = :id")
    suspend fun deleteById(id: Int):Int

    @Query(
        "UPDATE community_information SET community_name = :name, geographical_district = :geographical_district," +
                " accessibility = :accessibility, distance_ecom = :distance, connected_to_ecg = :connected_to_ecg," +
                " date_licence = :date_licence, latitude = :latitude, longitude= :longitude," +
                " image= :image, update_by= :updateBy," +
                " update_date= :updateDate, sent_server= :sent_server WHERE id = :id"
    )
    fun updateInformation(
        name: String?, id: Int, geographical_district: Int,
        accessibility: Int, distance: Int, connected_to_ecg: String?,
        date_licence: String?, latitude: Double?, longitude: Double?, image: String?,
        updateBy: String?, updateDate: String?, sent_server: Boolean?
    )
}