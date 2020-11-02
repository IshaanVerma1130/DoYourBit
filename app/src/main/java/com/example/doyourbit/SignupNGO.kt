package com.example.doyourbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.util.Log
import kotlinx.android.synthetic.main.activity_signup_ngo.*
import kotlinx.android.synthetic.main.activity_signup_user.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class SignupNGO : AppCompatActivity() {

    val JSON = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_ngo)

        val signup = findViewById<Button>(R.id.SignUP_ngo_button)

        signup.setOnClickListener{

            val name = SignUP_ngo_name.text.toString()
            val email = SignUP_ngo_email.text.toString()
            val password = SignUP_ngo_pass.text.toString()
            val confirmPass = SignUP_ngo_cnfpass.text.toString()
            val address = SignUP_ngo_address.text.toString()
            val phone = SignUP_ngo_phone.text.toString()

            if(password != confirmPass) {
                Toast.makeText(this, "Passwords must match!", Toast.LENGTH_LONG).show()
            }
            else {

                val postBody = """ {"name": "$name", "email": "$email", "password": "$password", "address": "$address", "phone": "$phone"} """

                val client = OkHttpClient()
                val request = Request.Builder().url(Config().NGO_SIGNUP).post(postBody.toRequestBody(JSON)).build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val bodyString = response.body?.string()
                    //   println(bodyString)
                        val body = JSONObject(bodyString)

                        // Update UI on Main thread!
                        runOnUiThread(object : Runnable {
                            override fun run() {
                                if (response.code != 200) {
                                    val errors = body.getJSONArray("errors")
                                    val err: JSONObject = errors[0] as JSONObject
                                    Toast.makeText(
                                        applicationContext,
                                        err.getString("msg"),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else {
                                //    println("Signed up!")
                                    Toast.makeText(
                                        applicationContext,
                                        "Success!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(applicationContext, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        })

                    }

                    override fun onFailure(call: Call, e: IOException) {
                        call.cancel()
                    }
                })
            }
        }

    }
}