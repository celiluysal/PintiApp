package com.example.pintiapp.Models

import android.media.Image
import java.io.Serializable

data class ProductModel(
        val productName: String,
        val productBrand: String,
        val productPictureResource: Int,
        val recordCount: Int,
        val recordList: List<RecordModel>
) : Serializable
