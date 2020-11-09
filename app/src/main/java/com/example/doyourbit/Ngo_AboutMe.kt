package com.example.doyourbit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Ngo_AboutMe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo__about_me)

        val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)

        findViewById<TextView>(R.id.About_NgoInputName).text = sp.getString("n_name", "")
        findViewById<TextView>(R.id.About_NgoAddressInput).text = sp.getString("n_address", "")
        findViewById<TextView>(R.id.About_NgoAboutInput).text = sp.getString("n_about", "")
        findViewById<TextView>(R.id.About_NgoPhoneInput).text = sp.getString("n_phone", "")
        findViewById<TextView>(R.id.About_NgoEmailInput).text = sp.getString("n_email", "")


    }
}