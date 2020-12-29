package com.example.pintiapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
        @SerializedName("brand")
        val brand : String,
        @SerializedName("categoryId")
        val categoryId : String,
        @SerializedName("name")
        val name : String,
        @SerializedName("barcode")
        val barcode : String,
        @SerializedName("photoURL")
        val photoURL : String,
        @SerializedName("Records")
        var records : List<Record>
): Serializable