package com.example.pintiapp.ui.add_product

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.pintiapp.R
import com.example.pintiapp.base.BaseActivity
import com.example.pintiapp.databinding.ActivityAddProductBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.utils.*
import com.example.pintiapp.ui.main.MainActivity
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.util.*


class AddProductActivity : BaseActivity<ActivityAddProductBinding, AddProductViewModel>() {
    private val PRODUCT_PHOTO_RQ = 100
    private val PRICETAG_PHOTO_RQ = 101

    private lateinit var photoFile: File
    private val PHOTO_FILE_NAME = "photo.jpeg"

    private lateinit var auth: FirebaseAuth


    private lateinit var barcode: String
    private lateinit var locationCoordinate: String
    private var price: Double? = null

    override fun getViewBinding(): ActivityAddProductBinding {
        return ActivityAddProductBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        Log.e("user", auth.uid.toString())
        Log.e("user",  auth.currentUser?.email.toString())
        Log.e("user",  auth.currentUser?.displayName.toString())

        viewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)

     (R.id.textInputEditTextProductPrice)

        binding.progressBarPrice.visibility = ProgressBar.GONE

        val product: Product? = intent.extras?.get("product") as Product?
        barcode = intent.extras?.get("barcode") as String

        if (product != null)
            fillFields(product)

        setToolbar()
        getLocation()
        setSpinnerCategory()
        setSpinnerMarket()

        binding.cardViewAddProductDetail.visibility = CardView.GONE

        binding.cardViewAddPhoto.setOnClickListener {
            takePhoto(PRODUCT_PHOTO_RQ)
        }

        binding.cardViewAddPricetag.setOnClickListener {
            takePhoto(PRICETAG_PHOTO_RQ)
        }

        binding.buttonSave.setOnClickListener {
            if (checkFields()) {
                if (product != null) {
                    addRecord(barcode)
                } else {
                    addProduct(barcode)
                }
            }
        }


    }

    private fun fillFields(product: Product) {
        binding.textInputEditTextProductName.setText(product.name)
        binding.textInputEditTextProductName.isEnabled = false

        binding.textInputEditTextProductBrand.setText(product.brand)
        binding.textInputEditTextProductBrand.isEnabled = false

        viewModel.photoUrl.value = product.photoURL
        binding.imageViewAddPhoto.loadImage(product.photoURL, getProgressDrawable(this))
        binding.textViewAddPhoto.text = getString(R.string.photo_added)
        binding.cardViewAddPhoto.isEnabled = false

        binding.spinnerCategory.setSelection(product.categoryId.toInt())
        binding.spinnerCategory.isEnabled = false

    }

    private fun addProduct(barcode: String) {
        val name = binding.textInputEditTextProductName.text.toString()
        val brand = binding.textInputEditTextProductBrand.text.toString()
        val categoryId = binding.spinnerCategory.selectedItemPosition.toString()

        viewModel.addProduct(
            barcode, brand, categoryId,
            viewModel.photoUrl.value.toString(), name
        )

        viewModel.resultAddProduct.observe(this, {
            if (it.success) {
                addRecord(barcode)
            } else {
                Toast.makeText(this, "Kayıt başarısız", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun addRecord(barcode: String) {
        auth = FirebaseAuth.getInstance()
        viewModel.addRecord(
            barcode,
            auth.currentUser?.uid.toString(),
            auth.currentUser?.displayName.toString(),
            binding.spinnerMarket.selectedItemPosition.toString(),
            binding.textInputEditTextLocationTitle.text.toString(),
            locationCoordinate, price!!, getDateAndTime()
        )

        viewModel.resultAddRecord.observe(this, {
            if (it.success) {
                Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                finish()
//                parent.onBackPressed()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun takePhoto(requestCode: Int) {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)


        photoFile = File(storageDirectory, PHOTO_FILE_NAME)

        val fileProvider = FileProvider.getUriForFile(
            this,
            "com.example.pintiapp.fileprovider",
            photoFile
        )
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
        if (takePhotoIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(takePhotoIntent, requestCode)
        } else {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            var photo = BitmapFactory.decodeFile(photoFile.absolutePath)
            photo = originalRotate(photo, photoFile)
            photo = resizeBitmap(photo, 1024)

            if (requestCode == PRODUCT_PHOTO_RQ)
                uploadPhotoAndSetImageView(photo)
            else if (requestCode == PRICETAG_PHOTO_RQ)
                uploadPriceTagAndSetImageView(photo)

        } else
            super.onActivityResult(requestCode, resultCode, data)

    }


    private fun uploadPriceTagAndSetImageView(photo: Bitmap) {

        binding.imageViewAddPricetag.setImageBitmap(photo)
        binding.progressBarPrice.visibility = ProgressBar.VISIBLE
        viewModel.uploadPriceTag(barcode + " " + Date().toString(), photo)
        viewModel.priceTagUrl.observe(this, androidx.lifecycle.Observer {
//            imageViewAddPricetag.loadImage(it, getProgressDrawable(this))
            binding.cardViewAddProductDetail.visibility = CardView.VISIBLE
            binding.textViewAddPricetag.text = getString(R.string.pricetag_detecting)

            viewModel.getPrice(it)


            if (photoFile.exists())
                photoFile.delete()
        })

        viewModel.resultPriceTag.observe(this, {
            it.let {
                binding.textInputEditTextProductPrice.isEnabled = true
                binding.textInputEditTextProductPrice.setText(it.output)
                binding.textViewAddPricetag.text = getString(R.string.pricetag_scanned)

            }
        })

        viewModel.loadingPriceTag.observe(this, {
            if (it)
                binding.progressBarPrice.visibility = ProgressBar.VISIBLE
            else {
                binding.progressBarPrice.visibility = ProgressBar.GONE
            }
        })
    }

    private fun uploadPhotoAndSetImageView(photo: Bitmap) {
        binding.imageViewAddPhoto.setImageBitmap(photo)
        viewModel.uploadPhoto(barcode, photo)
        viewModel.photoUrl.observe(this, androidx.lifecycle.Observer {
//            imageViewAddPhoto.loadImage(it, getProgressDrawable(this))
            binding.textViewAddPhoto.text = getString(R.string.photo_added)
            if (photoFile.exists())
                photoFile.delete()

        })
    }

    private fun setToolbar() {
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

    private fun setSpinnerCategory() {
        val category_list = CategoryStatic.shared.getCategoryNameList()

        binding.spinnerCategory.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, category_list)
        binding.spinnerCategory.bringToFront()
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (category_list.get(p2) != getString(R.string.category)) {
                    binding.textInputEditTextSpinnerCategory.setText(" ")
                    binding.textInputSpinnerCategory.hint = getString(R.string.category)
                }
            }
        }
    }

    private fun setSpinnerMarket() {
        val market_list = ShopStatic.shared.getShopNameList()

        binding.spinnerMarket.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, market_list)
        binding.spinnerMarket.bringToFront()
        binding.textInputEditTextSpinnerMarket.isEnabled = false
        binding.spinnerMarket.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (market_list.get(p2) != getString(R.string.market)) {
                    binding.textInputEditTextSpinnerMarket.setText(" ")
                    binding.textInputSpinnerMarket.hint = getString(R.string.market)
                }
//                Toast.makeText(applicationContext, options.get(p2), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLocationTitle(location: Location) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        locationCoordinate = location.latitude.toString() + "," + location.longitude.toString()
        Log.e("title", locationCoordinate)
        val geoCoder = Geocoder(this, Locale.getDefault())
        val adress = geoCoder.getFromLocation(location.latitude, location.longitude, 1)

        Log.e("title", adress.toString())
        val thoroughfareText: String
        val subAdminAreaText: String
        val adminAreaText: String

        thoroughfareText = if (adress[0].thoroughfare.isNullOrEmpty()) ""
        else adress[0].thoroughfare.toString() + ", "

        subAdminAreaText = if (adress[0].subAdminArea.isNullOrEmpty()) ""
        else adress[0].subAdminArea.toString() + ", "

        adminAreaText = if (adress[0].adminArea.isNullOrEmpty()) ""
        else adress[0].adminArea.toString()

        val locationTitle = thoroughfareText + subAdminAreaText + adminAreaText

        binding.textInputEditTextLocationTitle.setText(locationTitle)
        Log.e("title", locationTitle)
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        if (viewModel.photoUrl.value.isNullOrEmpty()) {
            toast(getString(R.string.product_photo) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (binding.textInputEditTextProductName.text.toString().isNullOrBlank()) {
            toast(getString(R.string.product_name) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (binding.textInputEditTextProductBrand.text.toString().isNullOrBlank()) {
            toast(getString(R.string.brand) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (binding.spinnerCategory.selectedItem == getString(R.string.category)) {
            toast(getString(R.string.category) + " " + getString(R.string.unselected_spinner))
            return false
        }

        if (binding.spinnerMarket.selectedItem == getString(R.string.market)) {
            toast(getString(R.string.market) + " " + getString(R.string.unselected_spinner))
            return false
        }

        if (binding.textInputEditTextProductPrice.text.toString().isBlank()) {
            toast(getString(R.string.price) + " " + getString(R.string.field_cant_be_empty))
            return false
        } else {
            val priceValue = binding.textInputEditTextProductPrice.getText().toString().toDouble()
            price = priceValue
        }


        return true
    }

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private fun getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        var lastLocation: Location? = null
        if (isLocationEnabled()) {
            NewLocationData()
//            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
//                var location: Location? = task.result
//                if (location == null) {
//                    NewLocationData()
//                } else {
//                    setLocationTitle(location)
//                }
//            }
        } else {
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
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
//        locationRequest.fastestInterval = 0
//        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        lateinit var lastLocation: Location
        override fun onLocationResult(locationResult: LocationResult) {
            lastLocation = locationResult.lastLocation
            setLocationTitle(lastLocation)
//            Log.e("loc title ", lastLocation.toString())
            Log.e("loc title ", lastLocation.latitude.toString())

        }
    }



}