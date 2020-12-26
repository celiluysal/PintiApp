package com.example.pintiapp.models

import java.io.Serializable

class CategoryModel(val categoryName: String,
                    val categoryPictureResource: Int,
                    val productCount: Int,
) : Serializable