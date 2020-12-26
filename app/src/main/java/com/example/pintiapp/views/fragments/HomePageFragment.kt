package com.example.pintiapp.views.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pintiapp.views.adapters.ProductRecyclerViewAdapter
import com.example.pintiapp.models.ProductModel
import com.example.pintiapp.models.RecordModel
import com.example.pintiapp.views.ProductDetailActivity
import com.example.pintiapp.viewModels.HomePageViewModel
import com.example.pintiapp.R

class HomePageFragment : Fragment(), ProductRecyclerViewAdapter.OnProductItemClickListener {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private lateinit var viewModel: HomePageViewModel
    private lateinit var recyclerviewProducts: RecyclerView
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.home_page_fragment, container, false)

        setToolbar()





        recyclerviewProducts = rootView.findViewById(R.id.recyclerviewProducts)
        recyclerviewProducts.layoutManager = GridLayoutManager(activity, 2)
//        homeRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(),this)
        recyclerviewProducts.adapter = productRecyclerViewAdapter
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.refresh()

//        observeViewModel()

        // TODO: Use the ViewModel
    }

//    private fun observeViewModel() {
//        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
//            products?.let {
//                productRecyclerViewAdapter.updateProducts(it)
//                Log.e("fdg", "observe prducts")
//            }
//        })
//    }


    private fun setToolbar(){
        val m = (activity as AppCompatActivity)
        val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE
    }


    override fun onProductCardClick(item: ProductModel, position: Int) {

        Toast.makeText(activity, item.name.toString() , Toast.LENGTH_SHORT).show()
        activity?.let{
            val intent = Intent (it, ProductDetailActivity::class.java)
            intent.putExtra("product", item)
            it.startActivity(intent)
        }

    }
/*
    private fun createProductList(): ArrayList<ProductModel>{
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
        val record4 = RecordModel(
            "Hakmar",
            "Kemalpaşa mahallesi",
            "25.10.2020",
            "Ali Gündoğdu",
            "6.00"
        )

        val record_list1 = listOf(record1,record2,record3,record4)
        val record_list2 = listOf(record1,record2)
        val record_list3 = listOf(record4)
        val record_list4 = listOf(record1,record2,record3,record4,record1,record2)


        val luppo1 = ProductModel(
            "Luppo",
            "Ülker",
            R.drawable.luppo,
            record_list1.size,
            record_list1
        )
        val luppo2 = ProductModel(
            "Ülker çikolatalılılılıııııı gofret",
            "Ülker",
            R.drawable.luppo2,
            record_list2.size,
            record_list2
        )
        val luppo3 = ProductModel(
            "Luppo",
            "Ülker",
            R.drawable.luppo3,
            record_list3.size,
            record_list3
        )
        val luppo4 = ProductModel(
            "Ülker çikolatalı gofret",
            "Ülker",
            R.drawable.luppo4,
            record_list4.size,
            record_list4
        )
        return arrayListOf(luppo1,luppo2,luppo3,luppo4,luppo1,luppo2,luppo3,luppo4)

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

        return listOf(record1,record2)
    }
*/
}