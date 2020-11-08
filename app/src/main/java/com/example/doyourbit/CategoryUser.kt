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

//    val sp = getSharedPreferences("Info", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_user)

        toggle = ActionBarDrawerToggle(this, drawerLayout , R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        navView.setNavigationItemSelectedListener {
//           when(it.itemId){
//                R.id.Ngo_gallery -> Toast.makeText(applicationContext,
//                    "Clicked item 1",Toast.LENGTH_SHORT).show()
//                R.id.Ngo_about -> Toast.makeText(applicationContext,
//                    "Clicked item 2",Toast.LENGTH_SHORT).show()
//                R.id.Ngo_transaction -> Toast.makeText(applicationContext,
//                    "Clicked item 3",Toast.LENGTH_SHORT).show()
//            }
//            true }

        // update user name in nav drawer
        val view = User_navView.getHeaderView(0)
        view.user_header_name.text = Config.U_NAME//  sp.getString("u_name", "")

        // button for clothes
        val clothes = findViewById<ImageButton>(R.id.User_clothes)
        clothes.setOnClickListener {
            id = 1
            clicked()
        }

        // button for food
        val food = findViewById<ImageButton>(R.id.User_food)
        food.setOnClickListener{
            id = 2
            clicked()
        }

        // button for funds
        val funds = findViewById<ImageButton>(R.id.User_funds)
        funds.setOnClickListener{
            id = 3
            clicked()
        }

        // button for stationery
        val stationery = findViewById<ImageButton>(R.id.User_stationery)
        stationery.setOnClickListener{
            id = 4
            clicked()
        }

    }

    fun clicked(){

        val url = Config.USER_DONATE + id.toString()

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

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
                            val ngos = body.getJSONArray("ngo")
                            var name = ArrayList<String>()
                            var address = ArrayList<String>()
                            var phone = ArrayList<String>()

                            for (i in 0..(ngos.length() - 1)){

                                name.add(ngos.getJSONObject(i).getString("n_name").toString())
//                                Log.d("name", ngos.getJSONObject(i).getString("n_name").toString())
                                address.add(ngos.getJSONObject(i).getString("address").toString())
//                                Log.d("name", ngos.getJSONObject(i).getString("address").toString())
                                phone.add(ngos.getJSONObject(i).getString("phone").toString())
//                                Log.d("name", ngos.getJSONObject(i).getString("phone").toString())
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

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    }
