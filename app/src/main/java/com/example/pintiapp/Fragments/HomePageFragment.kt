package com.example.pintiapp.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val m = (activity as AppCompatActivity)
        val tb2 = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_2)
        val tb1 = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_1)

        tb1.visibility = Toolbar.INVISIBLE
        tb2.visibility = Toolbar.VISIBLE


        m.setSupportActionBar(tb2)
//        tb2.setNavigationIcon(R.drawable.ic_back)
//        tb2.setLogo(R.drawable.ic_pinti_white)
        tb2.setNavigationOnClickListener(View.OnClickListener {
            m.onBackPressed()
        })


        return inflater.inflate(R.layout.home_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)




//
//        val toolbar = (activity as AppCompatActivity).supportActionBar
//
//        toolbar?.title = "Ana sayfa"
//        toolbar?.setDisplayHomeAsUpEnabled(true)


        // TODO: Use the ViewModel
    }

}