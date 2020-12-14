package com.example.pintiapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddProductActivity : AppCompatActivity() {
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

    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val barcode = intent.getStringExtra("barcode")

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

        buttonSave = findViewById(R.id.buttonSave)

//        textInputSpinnerCategory.hint = null



        textInputEditTextProductName.setText("Luppo")

        getLocation()



//        textInputEditTextSpinnerCategory.setHintTextColor(Color.TRANSPARENT)

//        textInputEditTextSpinnerCategory.isEnabled = false


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


        buttonSave.setOnClickListener {
//            Toast.makeText(applicationContext, "buttoooooon", Toast.LENGTH_SHORT).show()
            checkFields()
        }

        if (barcode != null) {
            textInputEditTextProductName.setText(barcode.toString())
            textInputEditTextProductName.isEnabled = false
        }

        val main_tb = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(main_tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        main_tb.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }

    //Declaring the needed Variables
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010

    private fun getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//            Log.d("Debug:",CheckPermission().toString())
            Log.d("Debug:",isLocationEnabled().toString())
//            RequestPermission()
            /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
                 textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
             }*/
//            getLastLocation()
        NewLocationData()
    }

    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
//
//    fun getLastLocation(){
//            if(isLocationEnabled()){
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
//                    var location: Location? = task.result
//                    if(location == null){
//                        NewLocationData()
//                    }else{
//                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
//                        textInputEditTextLocationTitle.setText(getCityName(location.latitude,location.longitude))
//                    }
//                }
//            }else{
//                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
//            }
//    }

    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
//            textInputEditTextLocationTitle.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
//            textInputEditTextLocationTitle.setText(getCityName(lastLocation.latitude,lastLocation.longitude))
            textInputEditTextLocationTitle.setText(getCityName(lastLocation.latitude,lastLocation.longitude).toString())
        }
    }

    private fun getCityName(lat: Double,long: Double): MutableList<Address>? {
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0)?.locality.toString()
        countryName = Adress.get(0)?.countryName.toString()
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        Log.d("Debug:",Adress.get(1).toString())
        return Adress
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
}