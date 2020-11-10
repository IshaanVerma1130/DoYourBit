package com.example.doyourbit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_category_ngo.*
import kotlinx.android.synthetic.main.ngo_header.view.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class CategoryNGO : AppCompatActivity() {

    var id = 0
    val JSON = "application/json; charset=utf-8".toMediaType()
    lateinit var NGO_drawer: DrawerLayout

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_ngo)

        toggle = ActionBarDrawerToggle(this, NGO_drawerLayout, R.string.open, R.string.close)
        NGO_drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        NGO_drawer = findViewById(R.id.NGO_drawerLayout)
        val NGO_navView = findViewById<NavigationView>(R.id.NGO_navView)

        val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)
        val name = sp.getString("n_name", "")

        // Update name of ngo in nav drawer
        val view = NGO_navView.getHeaderView(0)
        view.ngo_header_name.text = name //Config.N_NAME

        // button for clothes
        val clothes = findViewById<ImageButton>(R.id.Ngo_clothes)
        clothes.setOnClickListener {
            id = 1
            clicked()
        }

        // button for food
        val food = findViewById<ImageButton>(R.id.Ngo_food)
        food.setOnClickListener {
            id = 2
            clicked()
        }

        // button for funds
        val funds = findViewById<ImageButton>(R.id.Ngo_funds)
        funds.setOnClickListener {
            id = 3
            clicked()
        }

        // button for stationery
        val stationery = findViewById<ImageButton>(R.id.Ngo_stationery)
        stationery.setOnClickListener {
            id = 4
            clicked()
        }

        NGO_navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
//                R.id.Ngo_gallery -> {
//
//                }
                R.id.Ngo_about -> {
                    val intent = Intent(this, Ngo_AboutMe::class.java)
                    startActivity(intent)
                }
//                R.id.Ngo_transaction -> {
//
//                }
                R.id.Ngo_logout -> {
                    sp.edit().clear().apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            false
        })
    }

    override fun onBackPressed() {
        if (NGO_drawer.isDrawerOpen(GravityCompat.START)){
            NGO_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun clicked() {

        val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)

        val url = Config.NGO_REQUEST + id.toString()

        val postBody = """ {"n_id": "${sp.getString("n_id", "")}" } """

        val client = OkHttpClient()
        val request = Request.Builder().url(url).post(postBody.toRequestBody(JSON)).build()

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
                            println("Successfully requested")
                            Toast.makeText(
                                applicationContext,
                                "Successfully requested",
                                Toast.LENGTH_SHORT
                            ).show()
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

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}