package com.example.doyourbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_signup_ngo.*

class ChoicePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        val ngo = findViewById<Button>(R.id.Choice_ngo)
        ngo.setOnClickListener{
            val intent = Intent(this, SignupNGO::class.java)
            startActivity(intent)
        }

        val user = findViewById<Button>(R.id.Choice_user)
        user.setOnClickListener{
            val intent = Intent(this, SignupUser::class.java)
            startActivity(intent)
        }
    }
}