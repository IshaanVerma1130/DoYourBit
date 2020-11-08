package com.example.doyourbit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ngo_list_row.view.*

class NgoList_Adapter: RecyclerView.Adapter<CustomViewHolder>() {

    val ngoNames = Config.nameList
    val ngoAddresses = Config.addressList
    val ngoPhone = Config.phoneList

    override fun getItemCount(): Int {
        return ngoNames.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.ngo_list_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val ngoName = ngoNames.get(position)
        val ngoAddress = ngoAddresses.get(position)
        val ngoPhone = ngoPhone.get(position)
        holder.itemView.Name.text = ngoName
        holder.itemView.Address.text = ngoAddress
        holder.itemView.Phone.text = ngoPhone
    }
}

class CustomViewHolder(v: View): RecyclerView.ViewHolder(v) {

}