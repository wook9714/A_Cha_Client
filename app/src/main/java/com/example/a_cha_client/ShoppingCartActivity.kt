package com.example.a_cha_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_cha_client.databinding.ActivityOrderListBinding
import com.example.a_cha_client.databinding.ActivityOrderPageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class OrderListActivity : AppCompatActivity() {
    companion object{
        lateinit var instance:OrderListActivity
    }
    val TAG : String = "로그"
    val binding by lazy { ActivityOrderListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        instance = this
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Functions.makeStatusBarTransparent(window)
        binding.continueButton.setOnClickListener {
           // val intentToMainActivity = Intent(this, MainActivity :: class.java)
            //startActivity(intentToMainActivity)
            finish()
        }

        var adapter = OrderListRecyclerAdapter()
        adapter.listData = MainActivity.usersShoppingCartList
        binding.orderListRecyclerView.adapter = adapter
        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(this)








    }

    override fun onResume() {
        super.onResume()
        updateShoppingListOnShoppingCartActivity()
    }

    fun updateShoppingListOnShoppingCartActivity(){
        MainActivity.usersShoppingCartForServer.shoppingListArray?.clear()
        MainActivity.usersShoppingCartList.clear()
        Log.d(TAG, "ShoppingListActivity : cleanShoppingCartByMap ${MainActivity.usersShoppingCartForServer}")
        Log.d(TAG, "ShoppingListActivity : cleanShoppingCart ${MainActivity.usersShoppingCartList}")

        MainActivity.shoppingCartRef.get().addOnSuccessListener {
            val data = it.toObject<ShoppingListData>()
            for(i in data!!.shoppingListArray!!){
                var menuName = (i!!.keys.toString().replace("[","")).replace("]","").toString()
                var quantity = (i!!.values.toString().replace(Regex("[^0-9]"),"").toInt())
                MainActivity.usersShoppingCartList.add(OrderListData(menuName, quantity))
            }
            MainActivity.usersShoppingCartForServer = data
            Log.d(TAG, "ShoppingListActivity : update shoppingCartByMap ${MainActivity.usersShoppingCartForServer}")
            Log.d(TAG, "ShoppingListActivity : update shoppingCart ${MainActivity.usersShoppingCartList}")
            binding.orderListRecyclerView.adapter!!.notifyDataSetChanged()
            sumAllPriceOfItems()
        }
    }

    fun sumAllPriceOfItems(){
        var sum = 0
        for(i in MainActivity.usersShoppingCartList){
            val menuPrice = MainActivity.loadedMenuData.find{it.name==i.menuName}?.price?:0
            Log.d("menuPrice","수량"+i.quantity.toString())
            Log.d("menuPrice","가격"+menuPrice.toString())
            Log.d("menuPrice","합:"+sum.toString())
            sum += i.quantity!! * menuPrice
        }

        binding.payButtonWithTotalPrice.text = sum.toString()+"원 결제"
    }



}