package com.example.pintiapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.pintiapp.R
import com.example.pintiapp.base.BaseActivity
import com.example.pintiapp.databinding.ActivityRegisterBinding
import com.example.pintiapp.ui.login.LoginActivity
import com.example.pintiapp.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : BaseActivity<ActivityRegisterBinding, ViewModel>() {
    private lateinit var auth: FirebaseAuth

    override fun getViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar()

        auth = Firebase.auth

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        binding.buttonRegister.setOnClickListener {
            if (checkFields())
                register(
                    binding.textInputEditTextEmail.text?.trim().toString(),
                    binding.textInputEditTextPassword.text?.trim().toString()
                )
        }
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        if (binding.textInputEditTextFullName.text.toString().isNullOrBlank()) {
            toast(getString(R.string.full_name) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (binding.textInputEditTextEmail.text.toString().isNullOrBlank()) {
            toast(getString(R.string.email) + " " + getString(R.string.field_cant_be_empty))
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.textInputEditTextEmail.text.toString()).matches()) {
            toast(getString(R.string.wrong_email_type))
            return false
        }

        if (binding.textInputEditTextPassword.text.toString().isNullOrBlank()) {
            toast(getString(R.string.password) + " " + getString(R.string.field_cant_be_empty))
            return false
        } else {
            if (binding.textInputEditTextPassword.text.toString().length < 6) {
                toast(getString(R.string.short_password))
                return false
            } else if (binding.textInputEditTextPassword.text.toString().length > 18) {
                toast(getString(R.string.long_password))
                return false
            } else if (binding.textInputEditTextPassword.text.toString() != binding.textInputEditTextPasswordAgain.text.toString()) {
                toast(getString(R.string.did_not_match_passwords))
                return false
            }
        }

        return true
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e("TAG", "createUserWithEmail:success")


                    Toast.makeText(
                        baseContext, "Authentication success.",
                        Toast.LENGTH_SHORT
                    ).show()

//                        Log.e("TAG", "user"+ user?.uid.toString())

                    val user = auth.currentUser
                    var profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(binding.textInputEditTextFullName.text.toString()).build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener(this) {
                        updateUI(user)
                    }


                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "failed:" + task.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(
                baseContext, "Oturum açılamadı.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.includeToolbar.mainToolbar)
        binding.includeToolbar.imageViewSearch.visibility = ImageView.INVISIBLE
        binding.includeToolbar.imageViewBack.visibility = ImageView.INVISIBLE
    }


}