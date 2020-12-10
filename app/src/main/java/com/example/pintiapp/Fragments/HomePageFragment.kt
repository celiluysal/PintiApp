package com.example.pintiapp.Fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.Adapters.ProductRecyclerViewAdapter
import com.example.pintiapp.Models.ProductModel
import com.example.pintiapp.Models.RecordModel
import com.example.pintiapp.ProductDetailActivity
import com.example.pintiapp.ViewModels.HomePageViewModel
import com.example.pintiapp.R

class HomePageFragment : Fragment(), ProductRecyclerViewAdapter.OnProductItemClickListener {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private lateinit var viewModel: HomePageViewModel
    private lateinit var recyclerviewProducts: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.home_page_fragment, container, false)

        set_toolbar()

        recyclerviewProducts = rootView.findViewById(R.id.recyclerviewProducts)
        recyclerviewProducts.layoutManager = GridLayoutManager(activity, 2)
//        homeRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerviewProducts.adapter = ProductRecyclerViewAdapter(createProductList(), this)
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


    override fun onProductCardClick(item: ProductModel, position: Int) {

        Toast.makeText(activity, item.productName.toString() , Toast.LENGTH_SHORT).show()
        activity?.let{
            val intent = Intent (it, ProductDetailActivity::class.java)
            intent.putExtra("product", item)
            it.startActivity(intent)
        }

    }

    private fun createProductList(): List<ProductModel>{
        val luppo1 = ProductModel(
                "Luppo",
                "Ülker",
                R.drawable.luppo,
                5,
                createRecordList()
        )
        val luppo2 = ProductModel(
                "Ülker çikolatalılılılıııııı gofret",
                "Ülker",
                R.drawable.luppo2,
                4,
                createRecordList()
        )
        val luppo3 = ProductModel(
                "Luppo",
                "Ülker",
                R.drawable.luppo3,
                7,
                createRecordList()
        )
        val luppo4 = ProductModel(
                "Ülker çikolatalı gofret",
                "Ülker",
                R.drawable.luppo4,
                2,
                createRecordList()
        )
        return listOf(luppo1,luppo2,luppo3,luppo4,luppo1,luppo2,luppo3,luppo4)

    }

    private fun createRecordList(): List<RecordModel>{
        val record1 = RecordModel(
                "A101",
                "Kemalpaşa mahallesi",
                "28.10.2020",
                "Celil Uysal",
                "5,75"
        )
        val record2 = RecordModel(
                "Şok",
                "Kemalpaşa mahallesi",
                "24.10.2020",
                "Emin Özkalaycıoğlu",
                "5,99"
        )
        val record3 = RecordModel(
                "BİM",
                "Kemalpaşa mahallesi",
                "27.10.2020",
                "Ali Gündoğdu",
                "4,25"
        )

        return listOf(record1,record2,record3)
    }

}