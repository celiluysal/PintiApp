package com.example.pintiapp.ui.add_product

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pintiapp.di.DaggerApiComponent
import com.example.pintiapp.models.OcrResult
import com.example.pintiapp.models.Result
import com.example.pintiapp.service.OcrService
import com.example.pintiapp.service.PintiService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class AddProductViewModel: ViewModel() {
    @Inject
    lateinit var pintiService: PintiService

    @Inject
    lateinit var ocrService: OcrService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposible = CompositeDisposable()

    val resultAddProduct = MutableLiveData<Result>()
    val resultAddRecord = MutableLiveData<Result>()

    val resultPriceTag = MutableLiveData<OcrResult>()
    val loadingPriceTag = MutableLiveData<Boolean>()

//    val success = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    val photoUrl = MutableLiveData<String>()
    val priceTagUrl = MutableLiveData<String>()





    fun getPrice(url: String) {
        loadingPriceTag.value = true
        disposible.add(
            ocrService.getPrice(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<OcrResult>() {
                    override fun onSuccess(value: OcrResult?) {
                        resultPriceTag.value = value
                        loadingPriceTag.value = false
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("getPrice e ", e.toString())
                        loadingPriceTag.value = false
                    }

                })
        )
    }


    fun addRecord(barcode: String, ownerid: String, ownername: String,
                  shopid: String, locationtitle: String, locationcoordinate: String,
                  price: Double, recorddate: String) {
        loading.value = true
        disposible.add(
            pintiService.addRecord(barcode, ownerid, ownername, shopid,
                locationtitle, locationcoordinate, price, recorddate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Result>() {
                    override fun onSuccess(value: Result?) {
                        value.let {
                            resultAddRecord.value = it
                        }
                    }

                    override fun onError(e: Throwable?) {
                        TODO("Not yet implemented")
                    }
                })
        )
    }

    fun addProduct(barcode: String, brand: String, categoryid: String,
                   photourl: String, name: String) {
        loading.value = true
        disposible.add(
            pintiService.addProduct(barcode, brand, categoryid, photourl, name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Result>() {
                    override fun onSuccess(value: Result?) {
                        value.let {
                            resultAddProduct.value = it
                        }
                    }

                    override fun onError(e: Throwable?) {
                        TODO("Not yet implemented")
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposible.clear()
    }


    fun uploadPhoto(name: String, photo: Bitmap) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val photoRef = storageRef.child("images/products/$name.jpeg")
        val baos = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.e("a", "addOnFailureListener")
        }.addOnSuccessListener {
            Log.e("a", "addOnSuccessListener")

            storageRef.child("images/products/$name.jpeg").downloadUrl.addOnSuccessListener {
                // Got the download URL for 'users/me/profile.png'
                Log.e("path: ", it.toString())
                photoUrl.value = it.toString()

            }.addOnFailureListener {
                // Handle any errors
            }
        }
    }

    fun uploadPriceTag(name: String, photo: Bitmap) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val photoRef = storageRef.child("images/price_tags/$name.jpeg")
        val baos = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.e("a", "addOnFailureListener")
        }.addOnSuccessListener {
            Log.e("a", "addOnSuccessListener")

            storageRef.child("images/price_tags/$name.jpeg").downloadUrl.addOnSuccessListener {
                // Got the download URL for 'users/me/profile.png'
                Log.e("path: ", it.toString())
                priceTagUrl.value = it.toString()
                Log.e("fdg","price tag")

            }.addOnFailureListener {
                // Handle any errors
            }
        }
    }



}