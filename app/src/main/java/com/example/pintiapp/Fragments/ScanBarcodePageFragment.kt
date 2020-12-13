package com.example.pintiapp.Fragments

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pintiapp.BarcodeScanActivity
import com.example.pintiapp.ViewModels.AddProductPageViewModel
import com.example.pintiapp.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class ScanBarcodePageFragment : Fragment() {

    companion object {
        fun newInstance() = ScanBarcodePageFragment()
    }

    private lateinit var viewModel: AddProductPageViewModel
    private lateinit var cardViewScanBarcode: CardView
    val CAMERA_RQ = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.scan_barcode_page_fragment, container, false)
        set_toolbar()

        cardViewScanBarcode = rootView.findViewById(R.id.cardViewScanBarcode)
        cardViewScanBarcode.setOnClickListener {
            checkForPermissions(android.Manifest.permission.CAMERA, "camera", CAMERA_RQ)
        }
        return rootView
    }


    private fun checkForPermissions(permission: String, name: String, requestCode: Int){
            when {
                activity?.let { ContextCompat.checkSelfPermission(it.applicationContext ,permission) } == PackageManager.PERMISSION_GRANTED -> {
//                    Toast.makeText(context, "$name permission granted", Toast.LENGTH_SHORT).show()
                    activity?.let{
                        val intent = Intent (it, BarcodeScanActivity::class.java)
                        it.startActivity(intent)
                    }
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> {
                    showDialog(permission, name, requestCode)
                    activity?.let { ActivityCompat.requestPermissions(it, arrayOf(permission), requestCode) }
                }
            }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddProductPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun set_toolbar(){
        val m = (activity as AppCompatActivity)
        val main_tb = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val tb1 = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_1)

        tb1.visibility = Toolbar.INVISIBLE
        main_tb.visibility = Toolbar.VISIBLE
        m.setSupportActionBar(main_tb)
        m.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}