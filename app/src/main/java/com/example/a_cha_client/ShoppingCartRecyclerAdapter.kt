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
        return OrderListHolder(binding,listData)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: OrderListHolder, position: Int) {
        val orderListData = listData.get(position)
        holder.setItem(orderListData,position)

        holder.binding.deleteMenuButton.setOnClickListener {
            val theRemovedItem: OrderListData = listData.get(position)
            //removing item from data base
            MainActivity.usersShoppingCartForServer.shoppingListArray?.removeAt(position)
            Log.d(TAG, "onBindViewHolder: ${MainActivity.usersShoppingCartForServer}")
            MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartForServer)
            listData.removeAt(position) // remove the item from list
            notifyItemRemoved(position) // notify the adapter about the removed item
            OrderListActivity.instance.sumAllPriceOfItems()
            notifyItemRangeChanged(position,itemCount)
        }

        var quantity = MainActivity.usersShoppingCartForServer.shoppingListArray!!.get(position)!!.values.toString().replace(Regex("[^0-9]"),"").toInt()
        Log.d("quantityTag", MainActivity.usersShoppingCartForServer.shoppingListArray!!.get(position)!!.values.toString())
        var price = MainActivity.loadedMenuData.find{it.name == listData[position].menuName}?.price?:0



        holder.binding.buttonMinus.setOnClickListener {
            if (quantity == 1){
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*price).toString()

            }else if (quantity != null) {
                quantity-=1
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*price).toString()
                MainActivity.usersShoppingCartForServer.shoppingListArray!![position]!!.replace(orderListData.menuName!!,quantity.toInt())
                MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartForServer)
                MainActivity.usersShoppingCartList[position].quantity = quantity
                Log.d(TAG, "update shoppingCartByMap ${MainActivity.usersShoppingCartForServer}")
                Log.d(TAG, "update shoppingCart ${MainActivity.usersShoppingCartList}")
            }

            OrderListActivity.instance.sumAllPriceOfItems()
        }
        if (quantity != null) {
            holder.binding.buttonPlus.setOnClickListener {
                quantity+=1
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*price).toString()
                MainActivity.usersShoppingCartForServer.shoppingListArray!![position]!!.replace(orderListData.menuName!!,quantity.toInt())
                MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartForServer)
                MainActivity.usersShoppingCartList[position].quantity = quantity
                Log.d(TAG, "update shoppingCartByMap ${MainActivity.usersShoppingCartForServer}")
                Log.d(TAG, "update shoppingCart ${MainActivity.usersShoppingCartList}")
                OrderListActivity.instance.sumAllPriceOfItems()
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

class OrderListHolder(val binding: ItemRecyclerOrderListBinding,val listData:MutableList<OrderListData>) : RecyclerView.ViewHolder(binding.root){
    @RequiresApi(Build.VERSION_CODES.N)
    fun setItem(orderListData: OrderListData, position: Int){



        var menuName = orderListData.menuName
        var quantity = 1
        quantity = orderListData.quantity.toString().toInt()
        binding.menuName.text = menuName
        binding.menuQuantityText.text = quantity.toString()
        var price = MainActivity.loadedMenuData.find{it.name == listData[position].menuName}?.price?:0
        binding.menuPrice.text = (quantity * price).toString()

    }
}