package com.example.pintiapp.Models

import java.io.Serializable

data class RecordModel(
        val marketName: String,
        val locationTitle: String,
        val recordDate: String,
        val recordOwner: String,
        val productPrice: String,
) : Serializable
