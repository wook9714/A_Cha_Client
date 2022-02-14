package com.example.a_cha_client

import com.example.a_cha_client.Auth.uid
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DataFunction {

    lateinit var userInfoData: UserInfoData

    val db = Firebase.firestore

    //장바구니 레퍼런스
    val shopping_basket_ref = db.collection("users").document(uid).collection("shopping_basket").document("recent_basket")

    val order_info_ref = db.collection("orderInfo")

    val user_ref = db.collection("users").document(uid)







}