package com.example.pintiapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pintiapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextFullName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextPasswordAgain: TextInputEditText
    private lateinit var textViewLogin: TextView
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setToolbar()

        auth = Firebase.auth

        editTextFullName = findViewById(R.id.textInputEditTextFullName)
        editTextEmail = findViewById(R.id.textInputEditTextEmail)
        editTextPassword = findViewById(R.id.textInputEditTextPassword)
        editTextPasswordAgain = findViewById(R.id.textInputEditTextPasswordAgain)

        textViewLogin = findViewById(R.id.textViewLogin)
        textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        buttonRegister = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            if (checkFields())
                register(
                    editTextEmail.text?.trim().toString(),
                    editTextPassword.text?.trim().toString()
                )

        }
    }

    private fun checkFields(): Boolean {
        fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

        if (editTextFullName.text.toString().isNullOrBlank()) {
            toast(getString(R.string.full_name) + " " + getString(R.string.field_cant_be_empty))
            return false
        }

        if (editTextEmail.text.toString().isNullOrBlank()) {
            toast(getString(R.string.email) + " " + getString(R.string.field_cant_be_empty))
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            toast(getString(R.string.wrong_email_type))
            return false
        }

        if (editTextPassword.text.toString().isNullOrBlank()) {
            toast(getString(R.string.password) + " " + getString(R.string.field_cant_be_empty))
            return false
        } else {
            if (editTextPassword.text.toString().length < 6) {
                toast(getString(R.string.short_password))
                return false
            } else if (editTextPassword.text.toString().length > 18) {
                toast(getString(R.string.long_password))
                return false
            } else if (editTextPassword.text.toString() != editTextPasswordAgain.text.toString()) {
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
                        .setDisplayName(editTextFullName.text.toString()).build()
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
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)

        setSupportActionBar(toolbar)

        imageViewSearch.visibility = ImageView.INVISIBLE
        imageViewBack.visibility = ImageView.INVISIBLE
    }
}