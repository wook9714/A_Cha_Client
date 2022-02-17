package com.example.a_cha_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_cha_client.databinding.ActivityHomeBinding
import com.example.a_cha_client.databinding.ActivityOrderDetailsBinding
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson

class OrderDetailsActivity : AppCompatActivity() {

    val binding by lazy { ActivityOrderDetailsBinding.inflate(layoutInflater) }
    var orderedData:MutableList<OrderInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Functions.makeStatusBarTransparent(window)


        var adapter = OrderDetailsRecyclerAdapter()
        binding.orderDetailsRecyclerView.adapter = adapter
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)

        orderedData = mutableListOf()
        DataFunction.user_ref.get().addOnSuccessListener {
            val data = it.toObject<UserInfoData>()!!
            if(data.orderIDs!=null){
                for(i in data.orderIDs!!){
                    DataFunction.db.collection("orderInfo").document(i).get().addOnSuccessListener {
                        if(it.data!=null){
                            val orderInfo = it.toObject<OrderInfo>()!!
                            orderedData.add(orderInfo)
                            adapter.listData.add(orderInfo)
                            adapter.notifyDataSetChanged()

                        }

                    }
                }

            }

            Log.d("orderDetailLog","완료")

        }







    }



}