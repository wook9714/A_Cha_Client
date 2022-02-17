package com.example.a_cha_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a_cha_client.databinding.ActivityJoinMemberBinding
import com.google.firebase.firestore.GeoPoint

class JoinMemberActivity : AppCompatActivity() {
    val binding by lazy{ActivityJoinMemberBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.joinMemberBtn.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val phoneNumber = binding.phoneInput.text.toString()
            val dong = binding.dongInput.text.toString()
            val ho = binding.hoInput.text.toString()
            val detailedLoc = dong+"동"+ho+"호"
            val userInfoData = UserInfoData(name,detailedLoc,null,null,phoneNumber,false)

            DataFunction.userInfoData = userInfoData
            DataFunction.db.collection("users").document(Auth.uid).set(userInfoData).addOnSuccessListener {

                val intentGoMain = Intent(this,MainActivity::class.java)
                startActivity(intentGoMain)
                LoginActivity.instance!!.finish()
                finish()

            }



        }

    }
}

