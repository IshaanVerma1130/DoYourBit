package com.example.doyourbit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup_user.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    val JSON = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sp = getSharedPreferences("Info", MODE_PRIVATE)

        if (sp.getString("type", "") == "ngo" && sp.getBoolean("logged", false)) {
            val intent = Intent(applicationContext, CategoryNGO::class.java)
            startActivity(intent)
            finish()

        } else if (sp.getString("type", "") == "user" && sp.getBoolean("logged", false)) {
            val intent = Intent(applicationContext, CategoryUser::class.java)
            startActivity(intent)
            finish()
        } else {

            val signup = findViewById<Button>(R.id.Login_signup_button)
            signup.setOnClickListener {
                val intent = Intent(this, ChoicePage::class.java)
                startActivity(intent)
            }

            val login = findViewById<Button>(R.id.Login_button)
            login.setOnClickListener {


                val email = Login_user_email.text.toString()
                val password = Login_user_pass.text.toString()

                val postBody = """ {"email": "$email", "password": "$password"} """

                val client = OkHttpClient()
                val request =
                    Request.Builder().url(Config.LOGIN).post(postBody.toRequestBody(JSON)).build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val bodyString = response.body?.string()
                        //    println(bodyString)
                        val body = JSONObject(bodyString)

                        // Update UI on Main thread!
                        runOnUiThread(object : Runnable {
                            override fun run() {
                                if (response.code != 200) {
                                    val errors = body.getJSONArray("errors")
                                    val err: JSONObject = errors[0] as JSONObject
                                    Toast.makeText(applicationContext, err.getString("msg"), Toast.LENGTH_SHORT).show()
                                } else {
//                                println("Signed up!")
                                    Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT).show()

                                    if (body.getString("type") == "ngo") {
                                        Config.N_ID = body.getString("n_id")
//                                        print("n_id:"+Config.N_ID)
                                        Config.TYPE = body.getString("type")
                                        Config.U_ID = ""
                                        Config.N_NAME = body.getString("n_name")

                                        sp.edit().putString("type", "ngo").apply()
                                        sp.edit().putBoolean("logged", true).apply()
//                                        sp.edit().putString("n_name", body.getString("n_name")).apply()
//                                        sp.edit().putString("n_id", body.getString("n_id")).apply()
                                        val intent = Intent(applicationContext, CategoryNGO::class.java)
                                        startActivity(intent)
                                        finish()

                                    } else if (body.getString("type") == "user") {
                                        Config.U_ID = body.getString("u_id")
                                        Config.TYPE = body.getString("type")
                                        Config.N_ID = ""
                                        Config.U_NAME = body.getString("u_name")

                                        sp.edit().putString("type", "user").apply()
                                        sp.edit().putBoolean("logged", true).apply()
//                                        sp.edit().putString("u_name", body.getString("u_name")).apply()

                                        val intent = Intent(applicationContext, CategoryUser::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
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