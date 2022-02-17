package com.example.a_cha_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a_cha_client.databinding.ActivityBillingBinding
import com.google.gson.Gson

class BillingActivity : AppCompatActivity() {
    val binding by lazy{ActivityBillingBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val price = intent.getIntExtra("price",0)
        //val dong = intent.getIntExtra("dong",0)
        //val ho = intent.getIntExtra("ho",0)
        val detailedLoc = DataFunction.userInfoData.detailedLocation?:""
        binding.priceText.text = price.toString()
        //binding.addressText.text = dong.toString()+"동"+ho.toString()+"호"
        binding.addressText.text = detailedLoc

        binding.billingButton.setOnClickListener {
            //결제 시스템 호출


            val orderInfo = OrderInfo()
            orderInfo.deliveryState = 0
            orderInfo.detailedLocation = detailedLoc
            orderInfo.userID = Auth.uid

            orderInfo.orderItems = Gson().toJson(DataFunction.userBasket)


            DataFunction.order_info_ref.add(orderInfo).addOnSuccessListener {
                val docId = it.id
                var orderedList = DataFunction.userInfoData.orderIDs
                if(orderedList!=null){
                    orderedList.add(docId)
                }
                else{
                    orderedList = mutableListOf(docId)
                }


                DataFunction.user_ref.update("orderIDs",orderedList).addOnSuccessListener {
                    setResult(RESULT_OK)
                    finish()
                }


            }




        }

    }
}