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
    val TAG : String = "로그"
    val binding by lazy { ActivityOrderListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Functions.makeStatusBarTransparent(window)
        binding.continueButton.setOnClickListener {
            val intentToMainActivity = Intent(this, MainActivity :: class.java)
            startActivity(intentToMainActivity)
        }

        var adapter = OrderListRecyclerAdapter()
        adapter.listData = MainActivity.usersShoppingCart
        binding.orderListRecyclerView.adapter = adapter
        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(this)








    }

    override fun onResume() {
        super.onResume()
        updateShoppingListOnShoppingCartActivity()
    }

    fun updateShoppingListOnShoppingCartActivity(){
        MainActivity.usersShoppingCartByMap.shoppingCart?.clear()
        MainActivity.usersShoppingCart.clear()
        Log.d(TAG, "ShoppingListActivity : cleanShoppingCartByMap ${MainActivity.usersShoppingCartByMap}")
        Log.d(TAG, "ShoppingListActivity : cleanShoppingCart ${MainActivity.usersShoppingCart}")

        MainActivity.shoppingCartRef.get().addOnSuccessListener {
            val data = it.toObject<TestDataClass>()
            for(i in data!!.shoppingCart!!){
                var menuName = i.key
                var quantity = i.value
                MainActivity.usersShoppingCart.add(OrderListData(menuName, quantity))
            }
            MainActivity.usersShoppingCartByMap = data
            Log.d(TAG, "ShoppingListActivity : update shoppingCartByMap ${MainActivity.usersShoppingCartByMap}")
            Log.d(TAG, "ShoppingListActivity : update shoppingCart ${MainActivity.usersShoppingCart}")
            binding.orderListRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }



}