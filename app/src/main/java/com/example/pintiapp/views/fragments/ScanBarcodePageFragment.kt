 package com.example.pintiapp.views.fragments

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pintiapp.views.BarcodeScanActivity
import com.example.pintiapp.viewModels.BarcodeScanViewModel
import com.example.pintiapp.R

 class ScanBarcodePageFragment : Fragment() {

    companion object {
        fun newInstance() = ScanBarcodePageFragment()
    }

    private lateinit var viewModel: BarcodeScanViewModel
    private lateinit var cardViewScanBarcode: CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.scan_barcode_page_fragment, container, false)

        setToolbar()

        cardViewScanBarcode = rootView.findViewById(R.id.cardViewScanBarcode)
        cardViewScanBarcode.setOnClickListener {
            activity?.let{
                val intent = Intent (it, BarcodeScanActivity::class.java)
                it.startActivity(intent)
            }
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BarcodeScanViewModel::class.java)
        // TODO: Use the ViewModel
    }

     private fun setToolbar(){
         val m = (activity as AppCompatActivity)
         val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
         val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
         val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

         imageViewSearch.visibility = ImageView.INVISIBLE
         imageViewBack.visibility = ImageView.INVISIBLE
     }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int): Boolean{

        //            if (checkForPermissions(android.Manifest.permission.CAMERA, "camera", CAMERA_RQ) and
//                    checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", LOCATION_RQ)) {
//                        activity?.let{
//                            val intent = Intent (it, AddProductActivity::class.java)
//                            it.startActivity(intent)
//                        }
        var isGranted = false
        when {
            activity?.let { ContextCompat.checkSelfPermission(it.applicationContext ,permission) } == PackageManager.PERMISSION_GRANTED -> {
                isGranted = true
            }
//                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

            else -> {
                showDialog(permission, name, requestCode)
                activity?.let { ActivityCompat.requestPermissions(it, arrayOf(permission), requestCode) }
                isGranted = false
            }
        }
        return isGranted
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = context?.let { AlertDialog.Builder(it) }

        builder?.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") {dialog, which ->
                activity?.let { ActivityCompat.requestPermissions(it, arrayOf(permission), requestCode) }
            }
        }
        val dialog = builder?.create()
        dialog?.show()
    }

}