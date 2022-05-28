package com.example.ecomkt.data_local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ecomkt.app.util.DateConverters

@Entity(tableName = "community_information")
data class DbCommunityInformation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "community_name") val communityName: String,
    @ColumnInfo(name = "geographical_district") val geographicalDistrict: Int,
    val accessibility: Int,
    @ColumnInfo(name = "distance_ecom") val distanceToECOM: Int,
    @ColumnInfo(name = "connected_to_ecg") val connectedToECG: String,
    @ColumnInfo(name = "date_licence") val licenseDate: String,
    val latitude:Double,
    val longitude: Double,
    val image: String,
    @ColumnInfo(name = "created_by") val createdBy: String,
    @ColumnInfo(name = "createdDate") @TypeConverters(DateConverters::class) val createdDate: String,
    @ColumnInfo(name = "update_by") val updateBy: String,
    @ColumnInfo(name = "update_date") @TypeConverters(DateConverters::class) val updateDate: String,
    @ColumnInfo(name = "sent_server") @Transient val sentServer:Boolean
)
