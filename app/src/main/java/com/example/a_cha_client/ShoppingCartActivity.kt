package com.example.a_cha_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_cha_client.databinding.ActivityOrderListBinding
import com.google.firebase.firestore.ktx.toObject

class OrderListActivity : AppCompatActivity() {
    val BillingRequestCode = 99
    companion object{
        lateinit var instance:OrderListActivity
    }
    private var sumPrice = 0
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


        //adapter.listData = MainActivity.usersShoppingCartList
        binding.orderListRecyclerView.adapter = adapter
        binding.orderListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.payButtonWithTotalPrice.setOnClickListener {
            val intent = Intent(this, BillingActivity::class.java)
            intent.putExtra("price",sumPrice)
            startActivityForResult(intent,BillingRequestCode)
        }








    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            when(requestCode){
                BillingRequestCode->{
                    //결제가 성공적으로 완료시 행동
                    //finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sumAllPriceOfItems()
        updateShoppingListOnShoppingCartActivity()
    }

    fun updateShoppingListOnShoppingCartActivity(){
        /*
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

         */
    }

    fun sumAllPriceOfItems(){
        var sum = 0
        for(i in DataFunction.userBasket.orderedItems!!){
            val menuPrice = i.first.price
            val quantity = i.second

            Log.d("sumAllPriceOfItems","price: "+menuPrice.toString())
            Log.d("sumAllPriceOfItems","quantity: "+quantity.toString())
            sum += menuPrice!!*quantity
        }
        sumPrice = sum
        Log.d("sumAllPriceOfItems",sumPrice.toString())


        binding.payButtonWithTotalPrice.text = sumPrice.toString()+"원 결제"
    }



}