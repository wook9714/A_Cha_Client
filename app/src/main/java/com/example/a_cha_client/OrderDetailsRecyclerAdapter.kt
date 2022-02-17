package com.example.a_cha_client

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a_cha_client.databinding.ItemRecyclerOrderDetailsBinding
import com.example.a_cha_client.databinding.ItemRecyclerViewBinding
import com.example.a_cha_client.databinding.OrderDetailsItemsMenuitemBinding
import com.google.gson.Gson

class OrderDetailsRecyclerAdapter : RecyclerView.Adapter<OrderDetailsHolder>() {
    var listData = mutableListOf<OrderInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsHolder {
        val binding = ItemRecyclerOrderDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsHolder, position: Int) {
        val orderInfo = listData.get(position)
        holder.setItem(orderInfo)

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

    fun setItem(orderInfo: OrderInfo){
        //binding.brandTextView.text = "brand Test"
        var sumPrice = 0
        var basketData = Gson().fromJson(orderInfo.orderItems,BasketData::class.java)
        var allOrderList:MutableList<MutableList<OrderInfo>> = mutableListOf()

        var storeList = basketData.orderedItems!!.groupBy { it.first.storeName }
        Log.d("detailTag",storeList.size.toString())
        binding.orderMenuListLinearLayout.removeAllViews()
        for(i in storeList){

            var menuItemBinding = OrderDetailsItemsMenuitemBinding.inflate(LayoutInflater.from(binding.root.context))
            menuItemBinding.brandTextView.text = i.key

            var menuText:String = ""
            for(j in i.value){
                menuText+=j.first.name + " " + j.second + "개, "
            }
            menuItemBinding.menuDetailText.text = menuText

            binding.orderMenuListLinearLayout.addView(menuItemBinding.root)

            //

        }

        basketData.orderedItems!!.find { it.first.storeName == "죠샌드위치" }!!.toList()

        for(i in basketData.orderedItems!!){
            sumPrice += i.first.price!!*i.second
        }
        binding.sumPrice.text = sumPrice.toString()
    }
}