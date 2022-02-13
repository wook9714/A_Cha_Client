package com.example.a_cha_client

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.a_cha_client.databinding.ActivityMainBinding
import com.example.a_cha_client.databinding.ActivityOrderListBinding
import com.example.a_cha_client.databinding.ActivityOrderPageBinding

class OrderPageActivity : AppCompatActivity() {

    val binding by lazy { ActivityOrderPageBinding.inflate(layoutInflater) }
    val TAG : String = "로그"



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d(TAG, "OrderPageActivity - onCreate() called")

        Functions.makeStatusBarTransparent(window)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        
/*
        binding.buttonAddOrderList.setOnClickListener {
            val intentToOrderList = Intent(this, OrderListActivity::class.java)
            //여기서 서버의 최근장바구니 수량 수정해줘야함


            for (i in MainActivity.usersShoppingCart) {
                if (i.menuName == menuName!!) {

                    quantity += i.quantity!!
                    i.quantity = quantity
                    Log.d(TAG, "Quantity = $quantity")
                    MainActivity.usersShoppingCartByMap.shoppingCart!!.remove(menuName)
                    MainActivity.usersShoppingCartByMap.shoppingCart!!.set(menuName, quantity)
                    MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartByMap)

                    Log.d(TAG, "shoppingCart : " + MainActivity.usersShoppingCart.toString())
                    Log.d(TAG, "shoppingCartByMap : " + MainActivity.usersShoppingCartByMap.toString()
                    )
                    quantity = 1
                    Log.d(TAG, "Quantity = $quantity")
                    binding.menuQuantity.text = quantity.toString()
                    binding.menuTotalPrice.text = (quantity * menuPrice).toString()
                    startActivity(intentToOrderList)
                }


            }


        }
        
 */
    }

    override fun onResume() {
        super.onResume()
        Functions.updateShoppingList()
        var newThingOrNot : Boolean = intent.getBooleanExtra("newThing?",true)

        Log.d(TAG, "onResume: newThingOrNot : $newThingOrNot")
        val menuName = intent.getStringExtra("menuName")
        binding.menuName.text = menuName
        val menuPrice = intent.getIntExtra("menuPrice",10000)
        //menuPrice MainActivity 에서 전달받아야함
        var quantity = 1
        binding.menuTotalPrice.text = (quantity*menuPrice).toString()
        binding.menuQuantity.text = quantity.toString()
        binding.menuPrice.text = menuPrice.toString()

        binding.buttonMinus.setOnClickListener {
            if (quantity == 1){
                binding.menuQuantity.text = quantity.toString()
                binding.menuTotalPrice.text = (quantity*menuPrice).toString()
            }else{
                quantity-=1
                binding.menuQuantity.text = quantity.toString()
                binding.menuTotalPrice.text = (quantity*menuPrice).toString()
            }
            Log.d(TAG, "Quantity : $quantity")
        }
        binding.buttonPlus.setOnClickListener {
            quantity+=1
            binding.menuQuantity.text = quantity.toString()
            binding.menuTotalPrice.text = (quantity*menuPrice).toString()

            Log.d(TAG, "Quantity : $quantity")
        }

        if (newThingOrNot == false){
            binding.buttonAddOrderList.setOnClickListener {
                val intentToOrderList = Intent(this, OrderListActivity::class.java)
                for (i in MainActivity.usersShoppingCart) {
                    if (i.menuName == menuName!!) {

                        quantity += i.quantity!!
                        i.quantity = quantity
                        Log.d(TAG, "Quantity = $quantity")
                        MainActivity.usersShoppingCartByMap.shoppingCart!!.remove(menuName)
                        MainActivity.usersShoppingCartByMap.shoppingCart!!.set(menuName, quantity)
                        MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartByMap)

                        Log.d(TAG, "shoppingCart : " + MainActivity.usersShoppingCart.toString())
                        Log.d(TAG, "shoppingCartByMap : " + MainActivity.usersShoppingCartByMap.toString()
                        )
                        quantity = 1
                        Log.d(TAG, "Quantity = $quantity")
                        binding.menuQuantity.text = quantity.toString()
                        binding.menuTotalPrice.text = (quantity * menuPrice).toString()
                        Log.d(TAG, "기존메뉴 수량변경")
                        startActivity(intentToOrderList)
                    }

                }
            }

        }else{
            binding.buttonAddOrderList.setOnClickListener {
                val intentToOrderList = Intent(this, OrderListActivity::class.java)
                MainActivity.usersShoppingCartByMap.shoppingCart!!.put(menuName!! ,quantity)
                MainActivity.shoppingCartRef.set(MainActivity.usersShoppingCartByMap)
                var orderListData = OrderListData(menuName, quantity)
                MainActivity.usersShoppingCart.add(orderListData)
                quantity = 1
                Log.d(TAG, "Quantity = $quantity")
                binding.menuQuantity.text = quantity.toString()
                binding.menuTotalPrice.text = (quantity*menuPrice).toString()
                startActivity(intentToOrderList)
                Log.d(TAG, "새로운메뉴추가")
                newThingOrNot = false
            }


        }


    }






}