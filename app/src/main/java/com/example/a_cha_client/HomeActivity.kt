package com.example.a_cha_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a_cha_client.databinding.ActivityHomeBinding
import com.example.a_cha_client.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Functions.makeStatusBarTransparent(window)

        binding.myInfoButton.setOnClickListener {
            //val intentToTestActivity = Intent(this, TestActivity :: class.java)
            //startActivity(intentToTestActivity)
        }

        binding.orderButton.setOnClickListener {
            val intentToMainActivity = Intent(this, MainActivity :: class.java)
            startActivity(intentToMainActivity)
        }

        binding.shoppingCartButton.setOnClickListener {
            val intentToOrderListActivity = Intent(this, OrderListActivity :: class.java)
            startActivity(intentToOrderListActivity)
        }

        binding.myOrderDetailsButton.setOnClickListener {
            val intentToOrderDetailsActivity = Intent(this, OrderDetailsActivity :: class.java)
            startActivity(intentToOrderDetailsActivity)
        }

    }
}