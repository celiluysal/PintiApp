package com.example.pintiapp.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ActivityMainBinding
import com.example.pintiapp.viewModels.HomePageViewModel
import com.example.pintiapp.viewModels.ToolbarViewModel
import com.example.pintiapp.views.activities.MainActivity
import com.example.pintiapp.views.activities.SearchActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CategoriesPageFragment : Fragment() {

    private lateinit var toolbarViewModel: ToolbarViewModel

    companion object {
        fun newInstance() = CategoriesPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.categories_page_fragment, container, false)

        setToolbar()
        setTabs(rootView)

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

    private fun setTabs(rootView: View){

        val viewPager:ViewPager2 = rootView.findViewById(R.id.categories_view_pager)
        val tabLayout = rootView.findViewById<TabLayout>(R.id.tablayout)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when(position) {
                    0 -> {
                        CategoriesTabFragment.newInstance()
                    }
                    1 -> {
                        MarketsTabFragment.newInstance()
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

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.categories)
                1 -> getString(R.string.markets)
                else -> getString(R.string.categories)
            }
        }.attach()


    }
}


