package com.example.doyourbit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ngo_list.*

class NgoList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo_list)

        recyclerView_NgoList.layoutManager = LinearLayoutManager(this)
        recyclerView_NgoList.adapter = NgoList_Adapter()
    }

//    fun fetchJson() {
//
//    }

}