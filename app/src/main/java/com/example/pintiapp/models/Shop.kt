package com.example.pintiapp.models

import com.google.gson.annotations.SerializedName

data class Shop(
        @SerializedName("shopId")
        val shopId: String,
        @SerializedName("shopName")
        val shopName: String,
        @SerializedName("photoURL")
        val photoURL: String
)
