package com.example.pintiapp.models

import java.io.Serializable

class MarketModel (val marketName: String,
                   val marketPictureResource: Int,
                   val productCount: Int,
) : Serializable