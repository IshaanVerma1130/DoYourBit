package com.example.doyourbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.util.Log

class SignupNGO : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_ngo)

        val signup = findViewById<Button>(R.id.SignUP_ngo_button)
        signup.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            //Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show();
//            Log.d("log","logged")
        }

    }
}