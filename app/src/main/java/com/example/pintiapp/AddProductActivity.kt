package com.example.pintiapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddProductActivity : AppCompatActivity() {
    private val PRODUCT_PHOTO_RQ = 100
    private val PRICETAG_PHOTO_RQ = 101

    private lateinit var textInputEditTextProductName: TextInputEditText
    private lateinit var textInputEditTextProductBrand: TextInputEditText
    private lateinit var textInputEditTextLocationTitle: TextInputEditText
    private lateinit var textInputEditTextProductPrice: TextInputEditText

    private lateinit var textInputEditTextSpinnerCategory: TextInputEditText
    private lateinit var textInputSpinnerCategory: TextInputLayout
    private lateinit var textInputEditTextSpinnerMarket: TextInputEditText
    private lateinit var textInputSpinnerMarket: TextInputLayout

    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerMarket: Spinner

    private lateinit var cardViewAddPhoto: CardView
    private lateinit var imageViewAddPhoto: ImageView
    private lateinit var textViewAddPhoto: TextView

    private lateinit var cardViewAddPricetag: CardView
    private lateinit var imageViewAddPricetag: ImageView
    private lateinit var textViewAddPricetag: TextView

    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        textInputEditTextProductName = findViewById(R.id.textInputEditTextProductName)
        textInputEditTextProductBrand = findViewById(R.id.textInputEditTextProductBrand)
        textInputEditTextLocationTitle = findViewById(R.id.textInputEditTextLocationTitle)
        textInputEditTextProductPrice = findViewById(R.id.textInputEditTextProductPrice)

        textInputEditTextSpinnerCategory = findViewById(R.id.textInputEditTextSpinnerCategory)
        textInputSpinnerCategory = findViewById(R.id.textInputSpinnerCategory)

        textInputEditTextSpinnerMarket = findViewById(R.id.textInputEditTextSpinnerMarket)
        textInputSpinnerMarket = findViewById(R.id.textInputSpinnerMarket)

        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerMarket = findViewById(R.id.spinnerMarket)

        cardViewAddPhoto = findViewById(R.id.cardViewAddPhoto)
        imageViewAddPhoto = findViewById(R.id.imageViewAddPhoto)
        textViewAddPhoto = findViewById(R.id.textViewAddPhoto)

        cardViewAddPricetag = findViewById(R.id.cardViewAddPricetag)
        imageViewAddPricetag = findViewById(R.id.imageViewAddPricetag)
        textViewAddPricetag = findViewById(R.id.textViewAddPricetag)

        buttonSave = findViewById(R.id.buttonSave)




        setToolbar()

        getLocation()

        setSpinnerCategory()
        setSpinnerMarket()

        cardViewAddPhoto.setOnClickListener {
            takePhoto(PRODUCT_PHOTO_RQ)
        }

        cardViewAddPricetag.setOnClickListener {
            takePhoto(PRICETAG_PHOTO_RQ)
        }

        buttonSave.setOnClickListener {
            checkFields()
        }


        val barcode = intent.getStringExtra("barcode")
        if (barcode != null) {
            textInputEditTextProductName.setText(barcode.toString())
            textInputEditTextProductName.isEnabled = false
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun takePhoto(requestCode: Int) {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)

        if (takePhotoIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePhotoIntent, requestCode)
        } else {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PRODUCT_PHOTO_RQ && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            setCardViewAddPhoto(photo)
        }
        else if (requestCode == PRICETAG_PHOTO_RQ && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            setCardViewAddPricetag(photo)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setCardViewAddPricetag(photo: Bitmap) {
        imageViewAddPricetag.setImageBitmap(photo)
        textViewAddPricetag.text = getString(R.string.pricetag_scanned)
    }

    private fun setCardViewAddPhoto(photo: Bitmap){
        imageViewAddPhoto.setImageBitmap(photo)
        textViewAddPhoto.text = getString(R.string.photo_added)
    }

    private fun setToolbar(){
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)

        setSupportActionBar(toolbar)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.VISIBLE

        imageViewBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setSpinnerCategory(){
        val category_list = arrayOf("Kategori","İçecekler", "siksuyu")

        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, category_list)
        spinnerCategory.bringToFront()
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (category_list.get(p2) != getString(R.string.category)){
                    textInputEditTextSpinnerCategory.setText(" ")
                    textInputSpinnerCategory.hint = getString(R.string.category)
                }

//                Toast.makeText(applicationContext, options.get(p2), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setSpinnerMarket(){
        val market_list = arrayOf("Market","A101", "BİM")

        spinnerMarket.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, market_list)
        spinnerMarket.bringToFront()
        textInputEditTextSpinnerMarket.isEnabled = false
        spinnerMarket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (market_list.get(p2) != getString(R.string.market)){
                    textInputEditTextSpinnerMarket.setText(" ")
                    textInputSpinnerMarket.hint = getString(R.string.market)
                }
//                Toast.makeText(applicationContext, options.get(p2), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLocationTitle(location: Location){
        val geoCoder = Geocoder(this, Locale.getDefault())
        val adress = geoCoder.getFromLocation(location.latitude, location.longitude,1)

        val locationTitle = adress[0].thoroughfare + ", " +
                adress[0].subAdminArea.toString() + ", " +
                adress[0].adminArea.toString()

        textInputEditTextLocationTitle.setText(locationTitle)
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        if (textInputEditTextProductName.text.toString().isNullOrBlank()){
            toast(getString(R.string.product_name) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (textInputEditTextProductBrand.text.toString().isNullOrBlank()){
            toast(getString(R.string.brand) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (spinnerCategory.selectedItem == getString(R.string.category)){
            toast(getString(R.string.category) + " " + getString(R.string.unselected_spinner))
            return false
        }

        if (spinnerMarket.selectedItem == getString(R.string.market)){
            toast(getString(R.string.market) + " " + getString(R.string.unselected_spinner))
            return false
        }

        if (textInputEditTextProductPrice.text.toString().isNullOrBlank()){
            toast(getString(R.string.price) + " " + getString(R.string.field_cant_be_empty))
            return false
        }



//        if (editTextPassword.text.toString().isNullOrBlank()) {
//            toast(getString(R.string.password) + " " + getString(R.string.field_cant_be_empty))
//            return false
//        }
//        else {
//            if (editTextPassword.text.toString().length < 6){
//                toast(getString(R.string.short_password))
//                return false
//            }
//            else if (editTextPassword.text.toString().length > 18){
//                toast(getString(R.string.long_password))
//                return false
//            }
//            else if (editTextPassword.text.toString() != editTextPasswordAgain.text.toString()){
//                toast(getString(R.string.did_not_match_passwords))
//                return false
//            }
//        }

        return true
    }

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private fun getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun isLocationEnabled():Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        var lastLocation: Location? = null
        if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.e("Debug:" ,"Your Location:"+ location.longitude)
                        setLocationTitle(location)
                    }
                }
            }else{
                Log.e("sfb", "location false")
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Konum Servisi Gerekli")
                builder.setMessage("Market konumu için konum sevisini aktif etmelisiniz.")
                builder.setPositiveButton("Tamam") { dialog, which ->
                    dialog.cancel()
                    onBackPressed()
                }
                builder.show()
            }
    }

    @SuppressLint("MissingPermission")
    private fun NewLocationData() {
        var lastLocation: Location? = null
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
//        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        lateinit var lastLocation: Location
        override fun onLocationResult(locationResult: LocationResult) {
            lastLocation = locationResult.lastLocation
            setLocationTitle(lastLocation)
        }
    }

}