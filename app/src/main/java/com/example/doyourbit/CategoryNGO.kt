package com.example.doyourbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_category_ngo.*

class CategoryNGO : AppCompatActivity() {

    var id = 0

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_ngo)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val clothes = findViewById<ImageButton>(R.id.Ngo_clothes)
        clothes.setOnClickListener {
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

        val food = findViewById<ImageButton>(R.id.Ngo_food)
        food.setOnClickListener{
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

        val funds = findViewById<ImageButton>(R.id.Ngo_funds)
        funds.setOnClickListener{
            Toast.makeText(this, "Please add something to search.", Toast.LENGTH_SHORT).show()
        }

        val stationery = findViewById<ImageButton>(R.id.Ngo_stationery)
        stationery.setOnClickListener{
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