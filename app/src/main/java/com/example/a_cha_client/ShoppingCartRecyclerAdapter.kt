package com.example.a_cha_client

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_cha_client.databinding.ItemRecyclerOrderListBinding
import androidx.annotation.RequiresApi


class OrderListRecyclerAdapter : RecyclerView.Adapter<OrderListHolder>() {

    //TODO //
    //var a=MainActivity.usersShoppingCartByMap.toList().toMutableList().sortBy { it.first }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListHolder {
        val binding = ItemRecyclerOrderListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderListHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: OrderListHolder, position: Int) {
        val orderData = DataFunction.userBasket.orderedItems!!.get(position)
        val menuInfo = orderData.first
        val price =  orderData.first.price?:0
        var quantity = orderData.second

        holder.setItem(orderData)


        holder.binding.deleteMenuButton.setOnClickListener {
            val theRemovedItem = orderData
            //removing item from data base
            DataFunction.userBasket.orderedItems!!.removeAt(position)
            DataFunction.saveBasket()
            OrderListActivity.instance.sumAllPriceOfItems()
            notifyDataSetChanged()


            /*
            MainActivity.usersShoppingCartForServer.shoppingListArray?.removeAt(position)
            Log.d(TAG, "onBindViewHolder: ${MainActivity.usersShoppingCartForServer}")
            MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartForServer)
            listData.removeAt(position) // remove the item from list
            notifyItemRemoved(position) // notify the adapter about the removed item
            OrderListActivity.instance.sumAllPriceOfItems()
            notifyItemRangeChanged(position,itemCount)

             */

        }


        //var quantity = MainActivity.usersShoppingCartForServer.shoppingListArray!!.get(position)!!.values.toString().replace(Regex("[^0-9]"),"").toInt()
        //Log.d("quantityTag", MainActivity.usersShoppingCartForServer.shoppingListArray!!.get(position)!!.values.toString())
        //var price = MainActivity.loadedMenuData.find{it.name == listData[position].menuName}?.price?:0

        holder.binding.buttonPlus.setOnClickListener {
            quantity++
            holder.binding.menuQuantityText.text = quantity.toString()
            holder.binding.menuPrice.text = (quantity*price).toString()

            DataFunction.userBasket.orderedItems!![position] = menuInfo to quantity
            DataFunction.saveBasket()
            OrderListActivity.instance.sumAllPriceOfItems()

        }
        holder.binding.buttonMinus.setOnClickListener {
            if(quantity>1){
                quantity--
                holder.binding.menuQuantityText.text = quantity.toString()
                holder.binding.menuPrice.text = (quantity*price).toString()

                DataFunction.userBasket.orderedItems!![position] = menuInfo to quantity
                DataFunction.saveBasket()

                OrderListActivity.instance.sumAllPriceOfItems()
            }

        }

        /*

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



         */


    }



    override fun getItemCount(): Int {
        return DataFunction.userBasket.orderedItems?.size?:0
    }
}

class OrderListHolder(val binding: ItemRecyclerOrderListBinding) : RecyclerView.ViewHolder(binding.root){
    @RequiresApi(Build.VERSION_CODES.N)
    fun setItem(data:Pair<MenuInfo,Int>){


        var menuName = data.first.name
        var quantity = 1
        quantity = data.second
        binding.menuName.text = menuName
        binding.menuQuantityText.text = quantity.toString()
        var price = data.first.price?:0
        binding.menuPrice.text = (quantity * price).toString()

    }
}