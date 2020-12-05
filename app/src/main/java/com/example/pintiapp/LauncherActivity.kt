package com.example.pintiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val login = true
        var intent:Intent
        if (login)
            intent = Intent(this, MainActivity::class.java)
        else
            intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)

    }


}