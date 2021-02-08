package com.example.pintiapp.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pintiapp.ui.login.LoginActivity
import com.example.pintiapp.R
import com.example.pintiapp.databinding.ProfilePageFragmentBinding
import com.example.pintiapp.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfilePageFragment : BaseFragment<ProfilePageFragmentBinding, ProfilePageViewModel>() {

    companion object {
        fun newInstance() = ProfilePageFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProfilePageFragmentBinding {
        return ProfilePageFragmentBinding.inflate(inflater, container, false)
    }

//    private lateinit var binding: ProfilePageFragmentBinding
//    private lateinit var viewModel: ProfilePageViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val rootView = binding.root

        auth = Firebase.auth
        val user = auth.currentUser

        binding.textViewMail.text = user?.email.toString()
        binding.textViewFullName.text = user?.displayName.toString()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




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