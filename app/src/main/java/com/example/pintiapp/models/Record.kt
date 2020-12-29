package com.example.pintiapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Record(
        @SerializedName("barcode")
        val barcode : String,
        @SerializedName("locationCoordinate")
        val locationCoordinate : String,
        @SerializedName("locationTitle")
        val locationTitle : String,
        @SerializedName("ownerId")
        val ownerId : String,
        @SerializedName("ownerName")
        val ownerName : String,
        @SerializedName("price")
        val price : Double,
        @SerializedName("recordDate")
        val recordDate : String,
        @SerializedName("shopId")
        val shopId : String
): Serializable
