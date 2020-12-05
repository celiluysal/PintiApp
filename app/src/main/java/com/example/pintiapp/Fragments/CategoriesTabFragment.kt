package com.example.pintiapp.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pintiapp.R
import com.example.pintiapp.ViewModels.CategoriesTabViewModel

class CategoriesTabFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesTabFragment()
    }

    private lateinit var viewModel: CategoriesTabViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.categories_tab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesTabViewModel::class.java)
        // TODO: Use the ViewModel
    }

}