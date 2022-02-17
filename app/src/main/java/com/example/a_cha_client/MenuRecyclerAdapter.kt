package com.example.a_cha_client

import android.app.ProgressDialog.show
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a_cha_client.databinding.ActivityMainBinding
import com.example.a_cha_client.databinding.ItemRecyclerViewBinding
import com.google.gson.Gson

class MenuRecyclerAdapter : RecyclerView.Adapter<MenuHolder>() {
    var listData = mutableListOf<MenuInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val binding = ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val menuInformation = listData.get(position)
        holder.setItem(menuInformation)
        holder.itemView.setOnClickListener {

            val intentToOrderPage= Intent(holder.itemView?.context, OrderPageActivity ::class.java)
            for (i in MainActivity.usersShoppingCartList){
                if(i.menuName == menuInformation.name){
                    intentToOrderPage.putExtra("newThing?", false)
                }
            }
            intentToOrderPage.putExtra("menuJonData",Gson().toJson(menuInformation))
            intentToOrderPage.putExtra("menuName",menuInformation.name)
            intentToOrderPage.putExtra("storeName",menuInformation.storeName)
            intentToOrderPage.putExtra("menuPrice",menuInformation.price)
            intentToOrderPage.putExtra("menuDescription",menuInformation.description)
            ContextCompat.startActivity(holder.itemView.context,intentToOrderPage, null)

        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

class MenuHolder(val binding: ItemRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.root.setOnClickListener {

        }
    }


    fun setItem(menuInformation: MenuInfo){
        binding.menuName.text = menuInformation.name
        binding.menuPrice.text = menuInformation.price.toString()
    }
}