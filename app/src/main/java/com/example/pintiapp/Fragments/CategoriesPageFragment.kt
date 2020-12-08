package com.example.pintiapp.Fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pintiapp.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CategoriesPageFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        set_toolbar()

        return inflater.inflate(R.layout.categories_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTabs()



        // TODO: Use the ViewModel
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewPager = (activity as AppCompatActivity).findViewById(R.id.categories_view_pager)

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
        tb1_text.text = "Kategoriler"
        tb1.setNavigationOnClickListener(View.OnClickListener {
            m.onBackPressed()
        })
    }

    private fun setTabs(){

        val viewPager:ViewPager2 = (activity as AppCompatActivity).findViewById(R.id.categories_view_pager)
        val tabLayout = (activity as AppCompatActivity).findViewById<TabLayout>(R.id.tablayout)

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


