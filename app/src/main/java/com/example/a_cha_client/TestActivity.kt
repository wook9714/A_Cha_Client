package com.example.a_cha_client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val TAG : String = "로그"

        var testShoppingCart = MainActivity.db.collection("testCollection").document("uid").collection("testShoppingCart")

        val testData1 = hashMapOf(
            "햄치즈샌드위치" to 2,
            "과일한컵" to 1,
            "클럽샌드위치" to 3
            )

        testShoppingCart.document("TestSC1").set(testData1)


        var shoppingCartEx = mutableMapOf<String,Int>(
            "햄치즈샌드위치" to 2,
            "과일한컵" to 1,
            "클럽샌드위치" to 3
        )


        val testDataClass1 = TestDataClass(shoppingCartEx)
        testShoppingCart.document("최근장바구니").set(testDataClass1)








        val testRef = MainActivity.db.collection("testCollection").document("uid").collection("testShoppingCart")

        testRef.document("TestSC1").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        testRef.document("TestSC2").get()
            .addOnSuccessListener { documentSnapshot ->
                val toTestDataClass = documentSnapshot.toObject<TestDataClass>()
                Log.d(TAG, toTestDataClass.toString())
                for (i in toTestDataClass!!.shoppingCart!!){
                    Log.d(TAG, i.key)
                    Log.d(TAG, i.value.toString())
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        Log.d(TAG, MainActivity.usersShoppingCart.toString() + "1")
        Log.d(TAG, MainActivity.usersShoppingCartByMap.toString() +"2")

        var testUploadData = MutableArrayClass(testingArray = mutableListOf(mutableMapOf("test" to 2), mutableMapOf("test" to 3)))
        MainActivity.db.collection("testCollection").document("uid").collection("testShoppingCart").document("최근장바구니 with 정렬순서")
            .set(testUploadData)

        MainActivity.db.collection("testCollection").document("uid").collection("testShoppingCart").document("최근장바구니 with 정렬순서")
            .get().addOnSuccessListener {
                Log.d(TAG,it.toString())
                var d = it.data!!.values
                for (i in d){
                    Log.d(TAG, i.toString())

                }
            }




    }
}