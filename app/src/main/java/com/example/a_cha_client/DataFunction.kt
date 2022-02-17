package com.example.a_cha_client

import com.example.a_cha_client.Auth.uid
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

object DataFunction {

    val userBasket= BasketData(mutableListOf())

    lateinit var userInfoData: UserInfoData

    val db = Firebase.firestore

    //장바구니 레퍼런스
    val shopping_basket_ref = db.collection("users").document(uid).collection("shopping_basket").document("recent_basket")

    val order_info_ref = db.collection("orderInfo")

    val user_ref = db.collection("users").document(uid)



    fun saveBasket(function:Unit? = null){
        val jsonData = JsonStringData(Gson().toJson(userBasket))
        shopping_basket_ref.set(jsonData).addOnSuccessListener {
            function
        }
    }

    fun loadBasket(function: Unit? = null){
        shopping_basket_ref.get().addOnSuccessListener {
            if(it.data!=null){
                val jsonData = it.toObject<JsonStringData>()!!
                val data = Gson().fromJson(jsonData.jsonData,BasketData::class.java)
                userBasket.orderedItems = data.orderedItems
            }
            function
        }

    }






}