package com.example.pintiapp.views.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pintiapp.views.adapters.ProductRecyclerViewAdapter
import com.example.pintiapp.views.activities.ProductDetailActivity
import com.example.pintiapp.viewModels.HomePageViewModel
import com.example.pintiapp.R
import com.example.pintiapp.databinding.HomePageFragmentBinding
import com.example.pintiapp.models.Product
import com.example.pintiapp.utils.CategoryStatic
import com.example.pintiapp.utils.ShopStatic
import com.example.pintiapp.views.activities.MainActivity

class HomePageFragment : Fragment(), ProductRecyclerViewAdapter.OnProductItemClickListener {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    private var _binding: HomePageFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomePageViewModel
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = HomePageFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        setToolbar()

        binding.includeLayout.progressBarProduct.visibility = ProgressBar.VISIBLE
        binding.includeLayout.textViewNotFound.visibility = TextView.GONE

        binding.includeLayout.recyclerviewProducts.layoutManager = GridLayoutManager(activity, 2)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter(arrayListOf(), this)
        binding.includeLayout.recyclerviewProducts.adapter = productRecyclerViewAdapter

        binding.includeLayout.swipeRefreshLayoutShowProduct.setOnRefreshListener {
            binding.includeLayout.swipeRefreshLayoutShowProduct.isRefreshing = false
            viewModel.refresh()
            observeViewModel()
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.refresh()

        ShopStatic.shared.refresh()
        CategoryStatic.shared.refresh()

        ShopStatic.shared.shops.observe(viewLifecycleOwner, Observer {
            observeViewModel()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let { products ->
                productRecyclerViewAdapter.updateProducts(products)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.includeLayout.progressBarProduct.visibility = ProgressBar.VISIBLE
            else
                binding.includeLayout.progressBarProduct.visibility = ProgressBar.GONE
        })

        viewModel.loadError.observe(viewLifecycleOwner, Observer {
            if (it)
                binding.includeLayout.textViewNotFound.visibility = TextView.VISIBLE
            else
                binding.includeLayout.textViewNotFound.visibility = TextView.GONE

        })
    }


    private fun setToolbar() {



        val m = (activity as AppCompatActivity)
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