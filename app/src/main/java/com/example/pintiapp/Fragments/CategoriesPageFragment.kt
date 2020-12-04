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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        set_toolbar()

        return inflater.inflate(R.layout.categories_page_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesPageViewModel::class.java)

        // TODO: Use the ViewModel
    }


    private fun set_toolbar(){
        val m = (activity as AppCompatActivity)
        val main_tb = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val tb1 = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_1)
        val tb1_text = m.findViewById<TextView>(R.id.toolbar_title)

        tb1.visibility = Toolbar.VISIBLE
        main_tb.visibility = Toolbar.INVISIBLE
        m.setSupportActionBar(tb1)
        m.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tb1_text.text = "kategoriler"
        tb1.setNavigationOnClickListener(View.OnClickListener {
            m.onBackPressed()
        })
    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.main_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }


}