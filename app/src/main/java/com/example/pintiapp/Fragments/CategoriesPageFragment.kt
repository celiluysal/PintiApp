package com.example.pintiapp.Fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.pintiapp.ViewModels.CategoriesPageViewModel
import com.example.pintiapp.R

class CategoriesPageFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesPageFragment()
    }

    private lateinit var viewModel: CategoriesPageViewModel
    private lateinit var tb : Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val tb1 = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_1)
//        tb1?.visibility = Toolbar.INVISIBLE
//        tb1?.findViewById<TextView>(R.id.toolbar_title)?.text = "sfg"
//
//        val tb2 = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_2)
//        tb2?.visibility = Toolbar.VISIBLE

//
        val m = (activity as AppCompatActivity)
        val tb2 = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_2)
        val tb1 = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_1)
        val tb1_text = m.findViewById<TextView>(R.id.toolbar_title)
        tb2.visibility = Toolbar.INVISIBLE
        tb1.visibility = Toolbar.VISIBLE



        m.setSupportActionBar(tb1)
//        m.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        m.supportActionBar?.setDisplayShowHomeEnabled(true)
        tb1.setNavigationIcon(R.drawable.ic_back)
        tb1.setNavigationOnClickListener(View.OnClickListener {
            m.onBackPressed()
        })
        m.supportActionBar?.title = ""
        tb1_text.text = "kategoriler"


        return inflater.inflate(R.layout.categories_page_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesPageViewModel::class.java)

//        val tb2 = (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_2)
//        tb2.visibility = Toolbar.VISIBLE
//        val toolbar = (activity as AppCompatActivity).supportActionBar
//        val asd = (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbar_title)
//        asd.text = "sea"



//
//        toolbar?.title = "Kategoriler"


//        toolbar?.setLogo(R.drawable.ic_home)
//        toolbar?.setDisplayHomeAsUpEnabled(false)

        // TODO: Use the ViewModel
    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.main_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }


}