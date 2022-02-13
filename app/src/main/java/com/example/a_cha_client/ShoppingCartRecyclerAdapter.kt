package com.example.a_cha_client

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_cha_client.databinding.ItemRecyclerOrderListBinding
import androidx.annotation.RequiresApi


class OrderListRecyclerAdapter : RecyclerView.Adapter<OrderListHolder>() {
    var listData = mutableListOf<OrderListData>()

    //TODO //
    //var a=MainActivity.usersShoppingCartByMap.toList().toMutableList().sortBy { it.first }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListHolder {
        val binding = ItemRecyclerOrderListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderListHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: OrderListHolder, position: Int) {
        val orderListData = listData.get(position)
        holder.setItem(orderListData,position)

        holder.binding.deleteMenuButton.setOnClickListener {
            val theRemovedItem: OrderListData = listData.get(position)
            //removing item from data base
            MainActivity.usersShoppingCartByMap.shoppingCart?.remove(theRemovedItem.menuName)
            Log.d(TAG, "onBindViewHolder: ${MainActivity.usersShoppingCartByMap}")
            MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartByMap)
            listData.removeAt(position) // remove the item from list
            notifyItemRemoved(position) // notify the adapter about the removed item
            notifyItemRangeChanged(position,itemCount)
        }

        var quantity = MainActivity.usersShoppingCartByMap.shoppingCart!!.get(orderListData.menuName)

        holder.binding.buttonMinus.setOnClickListener {
            if (quantity == 1){
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*6100).toString()

            }else if (quantity != null) {
                quantity-=1
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*6100).toString()
                MainActivity.usersShoppingCartByMap.shoppingCart!!.replace(orderListData.menuName!!,quantity.toInt())
                MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartByMap)
                MainActivity.usersShoppingCart[position].quantity = quantity
                Log.d(TAG, "update shoppingCartByMap ${MainActivity.usersShoppingCartByMap}")
                Log.d(TAG, "update shoppingCart ${MainActivity.usersShoppingCart}")
            }


        }
        if (quantity != null) {
            holder.binding.buttonPlus.setOnClickListener {
                quantity+=1
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*6100).toString()
                MainActivity.usersShoppingCartByMap.shoppingCart!!.replace(orderListData.menuName!!,quantity.toInt())
                MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartByMap)
                MainActivity.usersShoppingCart[position].quantity = quantity
                Log.d(TAG, "update shoppingCartByMap ${MainActivity.usersShoppingCartByMap}")
                Log.d(TAG, "update shoppingCart ${MainActivity.usersShoppingCart}")

            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

class OrderListHolder(val binding: ItemRecyclerOrderListBinding) : RecyclerView.ViewHolder(binding.root){
    @RequiresApi(Build.VERSION_CODES.N)
    fun setItem(orderListData: OrderListData, position: Int){



        var menuName = orderListData.menuName
        var quantity = 1
        quantity = orderListData.quantity.toString().toInt()
        binding.menuName.text = menuName
        binding.menuQuantityText.text = quantity.toString()
        binding.menuPrice.text = (quantity * 6100).toString()

    }
}