package com.example.pintiapp.models

data class Record(
    val barcode: String,
    val location: String,
    val price: Double,
    val recordDate: String,
    val shopId: String
)