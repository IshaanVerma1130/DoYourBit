package com.example.doyourbit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.HeaderViewListAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_category_ngo.*
import kotlinx.android.synthetic.main.ngo_header.view.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException

class CategoryNGO : AppCompatActivity() {

    var id = 0
    val JSON = "application/json; charset=utf-8".toMediaType()

//    val sp = getSharedPreferences("Info", 0)

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_ngo)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Update name of ngo in nav drawer
        val view = NGO_navView.getHeaderView(0)
        view.ngo_header_name.text = Config.N_NAME //sp.getString("n_name", "")

        // button for clothes
        val clothes = findViewById<ImageButton>(R.id.Ngo_clothes)
        clothes.setOnClickListener {
            id = 1
            clicked()
        }

        // button for food
        val food = findViewById<ImageButton>(R.id.Ngo_food)
        food.setOnClickListener{
            id = 2
            clicked()
        }

        // button for funds
        val funds = findViewById<ImageButton>(R.id.Ngo_funds)
        funds.setOnClickListener{
            id = 3
            clicked()
        }

        // button for stationery
        val stationery = findViewById<ImageButton>(R.id.Ngo_stationery)
        stationery.setOnClickListener{
            id = 4
            clicked()
        }
    }

    private fun clicked(){

        val url = Config.NGO_REQUEST + id.toString()

        val postBody = """ {"n_id": "${Config.N_ID}" } """

        val client = OkHttpClient()
        val request = Request.Builder().url(url).post(postBody.toRequestBody(JSON)).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val bodyString = response.body?.string()
                val body = JSONObject(bodyString)

                runOnUiThread(object : Runnable {
                    override fun run() {
                        if (response.code != 200) {
                            val errors = body.getJSONArray("errors")
                            val err: JSONObject = errors[0] as JSONObject
                            Toast.makeText(applicationContext, err.getString("msg"), Toast.LENGTH_SHORT).show()
                        } else {
                            println("Successfully requested")
                            Toast.makeText(applicationContext,"Successfully requested", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}