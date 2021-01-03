package com.example.pintiapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pintiapp.R
import java.io.File
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

fun originalRotate(photo: Bitmap, file: File): Bitmap {
    var bitmap = photo
    val exif = ExifInterface(file.absoluteFile)
    var orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> bitmap = rotateBitmap(bitmap, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> bitmap = rotateBitmap(bitmap, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> bitmap = rotateBitmap(bitmap, 270f)
    }
    return bitmap
}

fun rotateBitmap(photo: Bitmap, degree: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree)
    return Bitmap.createBitmap(photo, 0, 0, photo.width, photo.height, matrix, true)
}

fun resizeBitmap(photo: Bitmap, maxLength: Int = 1024): Bitmap {
    val photoMaxLength = if (photo.width > photo.height) photo.width else photo.height
    val ratio = photoMaxLength.toFloat() / maxLength.toFloat()
    return Bitmap.createScaledBitmap(
        photo,
        (photo.width.toFloat() / ratio).toInt(),
        (photo.height.toFloat() / ratio).toInt(),
        false
    )
}