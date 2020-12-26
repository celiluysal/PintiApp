package com.example.pintiapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("photoURL")
    val photoURL: String,
    @SerializedName("Records")
    val recordList: List<RecordModel>
) : Serializable
