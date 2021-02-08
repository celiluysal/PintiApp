package com.example.pintiapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.pintiapp.R
import com.example.pintiapp.base.BaseActivity
import com.example.pintiapp.databinding.ActivityLoginBinding
import com.example.pintiapp.ui.main.MainActivity
import com.example.pintiapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : BaseActivity<ActivityLoginBinding, ViewModel>() {
    private lateinit var auth: FirebaseAuth
//    private lateinit var binding: ActivityLoginBinding

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setToolbar()

        auth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener {
            if (checkFields())
                login(binding.textInputEditTextEmailLogin.text.toString(), binding.textInputEditTextPasswordLogin.text.toString())
        }

        binding.textViewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        if (binding.textInputEditTextEmailLogin.text.toString().isNullOrBlank()) {
            toast(getString(R.string.email) + " " + getString(R.string.field_cant_be_empty))
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.textInputEditTextEmailLogin.text.toString()).matches()){
            toast(getString(R.string.wrong_email_type))
            return false
        }

        if (binding.textInputEditTextPasswordLogin.text.toString().isNullOrBlank()) {
            toast(getString(R.string.password) + " " + getString(R.string.field_cant_be_empty))
            return false
        }
        else {
            if (binding.textInputEditTextPasswordLogin.text.toString().length < 6){
                toast(getString(R.string.short_password))
                return false
            }
            else if (binding.textInputEditTextPasswordLogin.text.toString().length > 18){
                toast(getString(R.string.long_password))
                return false
            }
        }
        return true
    }

    private fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                        // ...
                    }

                    // ...
                }

    }


    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
            Toast.makeText(baseContext, "Oturum açılamadı.",
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun setToolbar(){
        setSupportActionBar(binding.includeToolbar.mainToolbar)
        binding.includeToolbar.imageViewSearch.visibility = ImageView.INVISIBLE
        binding.includeToolbar.imageViewBack.visibility = ImageView.INVISIBLE
    }


}