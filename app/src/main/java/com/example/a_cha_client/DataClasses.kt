package com.example.a_cha_client

data class MenuInformation(var name : String, var price : Int)

data class OrderListData(var menuName : String? = null, var quantity : Int? = null)

data class TestDataClass(
    var shoppingCart: MutableMap<String,Int>? = null
)

data class MutableArrayClass(
    var testingArray: MutableList<MutableMap<String, Int>?>? = null
)


