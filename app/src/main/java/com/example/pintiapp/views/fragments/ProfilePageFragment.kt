package com.example.pintiapp.views.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pintiapp.views.LoginActivity
import com.example.pintiapp.viewModels.ProfilePageViewModel
import com.example.pintiapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfilePageFragment : Fragment() {

    companion object {
        fun newInstance() = ProfilePageFragment()
    }

    private lateinit var viewModel: ProfilePageViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.profile_page_fragment, container, false)

        auth = Firebase.auth
        val user = auth.currentUser

        val textView3 = rootView.findViewById<TextView>(R.id.textView3)
        textView3.text = user?.email.toString()

        setToolbar()

        val buttonLogout = rootView.findViewById<Button>(R.id.buttonLogout)
        buttonLogout.setOnClickListener {

            Log.e("asd", "button click")
            auth.signOut()

            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfilePageViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setToolbar(){
        val m = (activity as AppCompatActivity)
        val toolbar = m.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = m.findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = m.findViewById<ImageView>(R.id.imageViewBack)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE
    }

}