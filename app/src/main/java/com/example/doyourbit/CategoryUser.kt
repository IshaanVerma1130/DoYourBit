package com.example.doyourbit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_category_user.*

class CategoryUser : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
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

        val stationery = findViewById<ImageButton>(R.id.User_stationery)
        stationery.setOnClickListener{
//            val intent = Intent(this, SignupUser::class.java)
//            startActivity(intent)
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

        val funds = findViewById<ImageButton>(R.id.User_funds)
        funds.setOnClickListener{
//            val intent = Intent(this, SignupUser::class.java)
//            startActivity(intent)
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

        val food = findViewById<ImageButton>(R.id.User_food)
        food.setOnClickListener{
//            val intent = Intent(this, SignupUser::class.java)
//            startActivity(intent)
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

        val clothes = findViewById<ImageButton>(R.id.User_clothes)
        clothes.setOnClickListener{
//            val intent = Intent(this, SignupUser::class.java)
//            startActivity(intent)
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    }
