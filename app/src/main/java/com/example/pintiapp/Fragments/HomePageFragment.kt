package com.example.pintiapp.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.pintiapp.ViewModels.HomePageViewModel
import com.example.pintiapp.R

class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private lateinit var viewModel: HomePageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        set_toolbar()

        return inflater.inflate(R.layout.home_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)

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