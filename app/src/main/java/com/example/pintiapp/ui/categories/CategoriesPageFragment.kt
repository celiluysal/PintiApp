package com.example.pintiapp.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pintiapp.R
import com.example.pintiapp.databinding.CategoriesPageFragmentBinding
import com.example.pintiapp.ui.categories.categories_tab.CategoriesTabFragment
import com.example.pintiapp.ui.categories.shops_tab.ShopsTabFragment
import com.example.pintiapp.ui.categories.search.SearchActivity
import com.example.pintiapp.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class CategoriesPageFragment : BaseFragment<CategoriesPageFragmentBinding, ViewModel>() {

//    private lateinit var binding: CategoriesPageFragmentBinding

    companion object {
        fun newInstance() = CategoriesPageFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CategoriesPageFragmentBinding {
        return CategoriesPageFragmentBinding.inflate(inflater,container,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = binding.root

        setToolbar()
        setTabs()

        return rootView
    }


    private fun setToolbar(){
        val m = (activity as AppCompatActivity)
        val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.VISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE

        m.setSupportActionBar(toolbar)
        imageViewSearch.setOnClickListener {
            activity?.let{
                val intent = Intent (it, SearchActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun setTabs(){
        binding.categoriesViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when(position) {
                    0 -> {
                        CategoriesTabFragment.newInstance()
                    }
                    1 -> {
                        ShopsTabFragment.newInstance()
                    }
                    else -> {
                        CategoriesTabFragment.newInstance()
                    }
                }
            }

            override fun getItemCount(): Int {
                return 2
            }
        }

        TabLayoutMediator(binding.tablayout, binding.categoriesViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.categories)
                1 -> getString(R.string.markets)
                else -> getString(R.string.categories)
            }
        }.attach()


    }


}


