package com.example.pintiapp.Fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.Adapters.HomeRecyclerViewAdaptor
import com.example.pintiapp.Models.ProductModel
import com.example.pintiapp.ViewModels.HomePageViewModel
import com.example.pintiapp.R

class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private lateinit var viewModel: HomePageViewModel
    private lateinit var homeRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.home_page_fragment, container, false)

        set_toolbar()

        homeRecyclerView = rootView.findViewById(R.id.home_recyclerview)
        homeRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        homeRecyclerView.adapter = HomeRecyclerViewAdaptor(createList())

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

    private fun createList(): List<ProductModel>{
        val luppo1 = ProductModel(
                "Luppo",
                "Ülker",
                R.drawable.luppo,
                5
        )
        val luppo2 = ProductModel(
                "Luppo",
                "Ülker",
                R.drawable.luppo2,
                1
        )
        val luppo3 = ProductModel(
                "Luppo",
                "Ülker",
                R.drawable.luppo3,
                7
        )
        val luppo4 = ProductModel(
                "Luppo",
                "Ülker",
                R.drawable.luppo4,
                2
        )
        val halley = ProductModel(
                "Halley",
                "Ülker",
                R.drawable.luppo,
                5
        )
        val cikolata = ProductModel(
                "Çikolata",
                "Ülker",
                R.drawable.luppo,
                4
        )

        return listOf(luppo1,luppo2,luppo3,luppo4,halley,cikolata,halley,cikolata)

    }

}