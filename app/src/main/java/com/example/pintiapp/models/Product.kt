package com.example.pintiapp.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("Records")
    val Records: List<Record>,
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photoURL")
    val photoURL: String
)