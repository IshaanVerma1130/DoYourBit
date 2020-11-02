package com.example.doyourbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.view.View
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signup = findViewById<Button>(R.id.Login_signup_button)
        signup.setOnClickListener{
            val intent = Intent(this, ChoicePage::class.java)
            startActivity(intent)
        }


    }
}