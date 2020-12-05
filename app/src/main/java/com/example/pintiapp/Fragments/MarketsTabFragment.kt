package com.example.pintiapp.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pintiapp.R
import com.example.pintiapp.ViewModels.MarketsTabViewModel

class MarketsTabFragment : Fragment() {

    companion object {
        fun newInstance() = MarketsTabFragment()
    }

    private lateinit var viewModel: MarketsTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.markets_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarketsTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}