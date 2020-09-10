package com.example.doyourbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_category_ngo.*

class category_ngo : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_ngo)

        toggle = ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.Item1 -> Toast.makeText(applicationContext,
//                    "Clicked item 1",Toast.LENGTH_SHORT).show()
//                R.id.Item2 -> Toast.makeText(applicationContext,
//                    "Clicked item 2",Toast.LENGTH_SHORT).show()
//                R.id.Item3 -> Toast.makeText(applicationContext,
//                    "Clicked item 3",Toast.LENGTH_SHORT).show()
//            }
//            true
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}