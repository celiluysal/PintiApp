package com.example.pintiapp.Fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.pintiapp.BarcodeScanActivity
import com.example.pintiapp.ViewModels.AddProductPageViewModel
import com.example.pintiapp.R

class ScanBarcodePageFragment : Fragment() {

    companion object {
        fun newInstance() = ScanBarcodePageFragment()
    }

    private lateinit var viewModel: AddProductPageViewModel
    private lateinit var cardViewScanBarcode: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.scan_barcode_page_fragment, container, false)
        set_toolbar()

        cardViewScanBarcode = rootView.findViewById(R.id.cardViewScanBarcode)
        cardViewScanBarcode.setOnClickListener {
            activity?.let{
                val intent = Intent (it, BarcodeScanActivity::class.java)
                it.startActivity(intent)
//                it.finish()
            }
        }



        return rootView
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