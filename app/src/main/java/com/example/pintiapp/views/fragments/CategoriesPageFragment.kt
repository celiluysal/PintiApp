package com.example.pintiapp.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pintiapp.R
import com.example.pintiapp.views.SearchActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CategoriesPageFragment : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)





        // TODO: Use the ViewModel
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewPager = (activity as AppCompatActivity).findViewById(R.id.categories_view_pager)

    }

    private fun setToolbar(){
        val m = (activity as AppCompatActivity)
        val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.VISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE

        m.setSupportActionBar(toolbar)
        imageViewBack.setOnClickListener {
            m.onBackPressed()
        }

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


