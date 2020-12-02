package com.example.pintiapp.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pintiapp.ViewModels.CategoriesPageViewModel
import com.example.pintiapp.R

class CategoriesPageFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesPageFragment()
    }

    private lateinit var viewModel: CategoriesPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.categories_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}