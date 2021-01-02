package com.example.pintiapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pintiapp.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        setColorSchemeColors(ContextCompat.getColor(context, R.color.orange))
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {

//    progressDrawable.setColorSchemeColors(ContextCompat.getColor(context, R.color.orange))

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.im_default)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun getPriceText(context: Context,price: Double): String {
    val priceFormat = DecimalFormat("#.00").format(price)
    return priceFormat.toString() + context.getString(R.string.price_symbol)
}

@SuppressLint("SimpleDateFormat")
fun getDateAndTime(): String {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
    return simpleDateFormat.format(Date())
}