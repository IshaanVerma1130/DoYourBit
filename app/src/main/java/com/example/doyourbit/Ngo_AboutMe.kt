package com.example.doyourbit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class Ngo_AboutMe : AppCompatActivity() {

    val JSON = "application/json; charset=utf-8".toMediaType()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo__about_me)

        val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)

        findViewById<TextView>(R.id.About_NgoInputName).text = sp.getString("n_name", "")
        findViewById<TextView>(R.id.About_NgoAddressInput).text = sp.getString("n_address", "")
        findViewById<TextView>(R.id.About_NgoAboutInput).text = sp.getString("n_about", "")
        findViewById<TextView>(R.id.About_NgoPhoneInput).text = sp.getString("n_phone", "")
        findViewById<TextView>(R.id.About_NgoEmailInput).text = sp.getString("n_email", "")

        val address = findViewById<Button>(R.id.update_address)
        val about = findViewById<Button>(R.id.update_about)
        val phone = findViewById<Button>(R.id.update_phone)

        address.setOnClickListener{
            edit_address()
        }

        about.setOnClickListener {
            edit_about()
        }

        phone.setOnClickListener{
            edit_phone()
        }
    }

    fun edit_about() {
        val dialog_about = layoutInflater.inflate(R.layout.dialog_about, null)
        val alert = AlertDialog.Builder(this)
        alert.setView(dialog_about)
        val alertDialog = alert.create()
        alertDialog.show()

        val okay = dialog_about.findViewById<Button>(R.id.bio_okay)
        val cancel = dialog_about.findViewById<Button>(R.id.bio_cancel)

        okay.setOnClickListener{
            val input_text = dialog_about.findViewById<EditText>(R.id.bio_input).text.toString()
            alertDialog.dismiss()
            clicked("about", input_text)
        }

        cancel.setOnClickListener{
            alertDialog.dismiss()
        }
    }

    fun edit_address() {
        val dialog_address = layoutInflater.inflate(R.layout.dialog_address, null)
        val alert = AlertDialog.Builder(this)
        alert.setView(dialog_address)
        val alertDialog = alert.create()
        alertDialog.show()

        val okay = dialog_address.findViewById<Button>(R.id.address_okay)
        val cancel = dialog_address.findViewById<Button>(R.id.address_cancel)

        okay.setOnClickListener {
            val input_text = dialog_address.findViewById<EditText>(R.id.address_input).text.toString()
            alertDialog.dismiss()
            clicked("address", input_text)
        }

        cancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun edit_phone() {
        val dialog_phone = layoutInflater.inflate(R.layout.dialog_phone, null)
        val alert = AlertDialog.Builder(this)
        alert.setView(dialog_phone)
        val alertDialog = alert.create()
        alertDialog.show()

        val okay = dialog_phone.findViewById<Button>(R.id.phone_okay)
        val cancel = dialog_phone.findViewById<Button>(R.id.phone_cancel)

        okay.setOnClickListener{
            val input_text = dialog_phone.findViewById<EditText>(R.id.phone_input).text.toString()
            alertDialog.dismiss()
            clicked("phone", input_text)
        }

        cancel.setOnClickListener{
            alertDialog.dismiss()
        }
    }

    private fun clicked(type: String, updated: String) {

        if (type == "phone") {

            val phone_number = updated.toBigInteger()
            val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)
            val n_id = sp.getString("n_id", "")
            val url = Config.NGO_UPDATE + type
            val postBody = """ {"n_id": "$n_id", "$type": $phone_number} """
            val client = OkHttpClient()
            val request = Request.Builder().url(url).patch(postBody.toRequestBody(JSON)).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body?.string()
                    val body = JSONObject(bodyString)

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
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    body.getString("status"),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val insert = "n_$type"
                                sp.edit().putString(insert, body.getString(type)).apply()
                                val intent = Intent(applicationContext, Ngo_AboutMe::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    })
                }

                override fun onFailure(call: Call, e: IOException) {
                    call.cancel()
                }
            })
        } else {

            print(updated)
            val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)
            val n_id = sp.getString("n_id", "")
            val url = Config.NGO_UPDATE + type
            val postBody = """ {"n_id": "$n_id", "$type": "$updated"} """
            val client = OkHttpClient()
            val request = Request.Builder().url(url).patch(postBody.toRequestBody(JSON)).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body?.string()
                    val body = JSONObject(bodyString)

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
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    body.getString("status"),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val insert = "n_$type"
                                sp.edit().putString(insert, body.getString(type)).apply()
                                val intent = Intent(applicationContext, Ngo_AboutMe::class.java)
                                startActivity(intent)
                                finish()
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