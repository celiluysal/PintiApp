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
import com.example.pintiapp.views.ProductDetailActivity
import com.example.pintiapp.viewModels.HomePageViewModel
import com.example.pintiapp.R
import com.example.pintiapp.models.Product

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
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        recyclerviewProducts.adapter = productRecyclerViewAdapter
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.refresh()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let { products ->
                productRecyclerViewAdapter.updateProducts(products)
            }
        })
    }


    private fun setToolbar() {
        val m = (activity as AppCompatActivity)
        val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE
    }


    override fun onProductCardClick(item: Product, position: Int) {

        Toast.makeText(activity, item.name.toString(), Toast.LENGTH_SHORT).show()
        activity?.let {
            val intent = Intent(it, ProductDetailActivity::class.java)
            intent.putExtra("product", item)
            it.startActivity(intent)
        }

    }

}