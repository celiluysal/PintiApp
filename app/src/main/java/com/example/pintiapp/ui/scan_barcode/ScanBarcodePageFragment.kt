package com.example.pintiapp.ui.scan_barcode

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ScanBarcodePageFragmentBinding
import com.example.pintiapp.base.BaseFragment

class ScanBarcodePageFragment : BaseFragment<ScanBarcodePageFragmentBinding, BarcodeScanViewModel>() {

    companion object {
        fun newInstance() = ScanBarcodePageFragment()
    }

//    private lateinit var binding: ScanBarcodePageFragmentBinding
//    private lateinit var viewModel: BarcodeScanViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ScanBarcodePageFragmentBinding {
        return ScanBarcodePageFragmentBinding.inflate(inflater, container, false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = binding.root

        setToolbar()

        binding.cardViewScanBarcode.setOnClickListener {
            activity?.let {
                val intent = Intent(it, BarcodeScanActivity::class.java)
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



    private fun setToolbar() {
        val m = (activity as AppCompatActivity)
        val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE
    }


}