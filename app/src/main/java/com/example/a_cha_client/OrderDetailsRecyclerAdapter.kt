package com.example.a_cha_client

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a_cha_client.databinding.ItemRecyclerOrderDetailsBinding
import com.example.a_cha_client.databinding.ItemRecyclerViewBinding

class OrderDetailsRecyclerAdapter : RecyclerView.Adapter<OrderDetailsHolder>() {
    var listData = mutableListOf<MenuInformation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsHolder {
        val binding = ItemRecyclerOrderDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsHolder, position: Int) {
        val menuInformation = listData.get(position)
        holder.setItem()

    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

class OrderDetailsHolder(val binding: ItemRecyclerOrderDetailsBinding) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.root.setOnClickListener {

        }
    }

    fun setItem(){

    }
}