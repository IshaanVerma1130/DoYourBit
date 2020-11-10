package com.example.doyourbit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_category_user.*
import kotlinx.android.synthetic.main.user_header.view.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class CategoryUser : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    var id = 0
    lateinit var User_drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_user)

        toggle = ActionBarDrawerToggle(this, User_drawerLayout, R.string.open, R.string.close)
        User_drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        User_drawer = findViewById(R.id.User_drawerLayout)
        val User_navView = findViewById<NavigationView>(R.id.User_navView)

        val sp = getSharedPreferences("Info", Context.MODE_PRIVATE)

        // update user name in nav drawer
        val view = User_navView.getHeaderView(0)
        view.user_header_name.text = sp.getString("u_name", "")

        // button for clothes
        val clothes = findViewById<ImageButton>(R.id.User_clothes)
        clothes.setOnClickListener {
            id = 1
            clicked()
        }

        // button for food
        val food = findViewById<ImageButton>(R.id.User_food)
        food.setOnClickListener {
            id = 2
            clicked()
        }

        // button for funds
        val funds = findViewById<ImageButton>(R.id.User_funds)
        funds.setOnClickListener {
            id = 3
            clicked()
        }

        // button for stationery
        val stationery = findViewById<ImageButton>(R.id.User_stationery)
        stationery.setOnClickListener {
            id = 4
            clicked()
        }

        User_navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            when (id) {
//                R.id.User_cust_care -> {
//
//                }
                R.id.User_about -> {
                    val intent = Intent(this, About_Us::class.java)
                    startActivity(intent)
                }
                R.id.User_logout -> {
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
        if (User_drawer.isDrawerOpen(GravityCompat.START)){
            User_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun clicked() {

        val url = Config.USER_DONATE + id.toString()

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

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
                            val ngos = body.getJSONArray("ngo")
                            var name = ArrayList<String>()
                            var address = ArrayList<String>()
                            var phone = ArrayList<String>()

                            for (i in 0..(ngos.length() - 1)) {

                                name.add(ngos.getJSONObject(i).getString("n_name").toString())
                                address.add(ngos.getJSONObject(i).getString("address").toString())
                                phone.add(ngos.getJSONObject(i).getString("phone").toString())
                            }

                            Config.nameList = name
                            Config.addressList = address
                            Config.phoneList = phone

                            val intent = Intent(applicationContext, NgoList::class.java)
                            startActivity(intent)
//                            Log.d("length  ", body.getJSONArray("ngo").getJSONObject(0).getString("n_id").toString())
//                            Log.d("ngozzz  ", body.getJSONArray("ngo").toString()
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
