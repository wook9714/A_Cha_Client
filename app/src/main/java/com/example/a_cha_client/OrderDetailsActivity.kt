package com.example.a_cha_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_cha_client.databinding.ActivityHomeBinding
import com.example.a_cha_client.databinding.ActivityOrderDetailsBinding

class OrderDetailsActivity : AppCompatActivity() {

    val binding by lazy { ActivityOrderDetailsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Functions.makeStatusBarTransparent(window)

        var adapter = OrderDetailsRecyclerAdapter()
        adapter.listData = Functions.loadData()
        binding.orderDetailsRecyclerView.adapter = adapter
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)



    }



}