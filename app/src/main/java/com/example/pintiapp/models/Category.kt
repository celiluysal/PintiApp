package com.example.pintiapp.models

import com.google.gson.annotations.SerializedName

data class Category(
        @SerializedName("categoryId")
        val categoryId: String,
        @SerializedName("categoryName")
        val categoryName: String,
        @SerializedName("photoURL")
        val photoURL: String
)