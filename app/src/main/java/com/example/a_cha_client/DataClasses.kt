package com.example.a_cha_client

data class MenuInformation(var name : String, var price : Int)

data class OrderListData(var menuName : String? = null, var quantity : Int? = null)

data class TestDataClass(
    var shoppingCart: MutableMap<String,Int>? = null
)

data class MutableArrayClass(
    var testingArray: MutableList<MutableMap<String, Int>?>? = null
)


//업체 메뉴에 대한 데이터 클래스
data class MenuItemClass(var name:String? = "", var price:Int? = 0,var description:String? = "", var imageLink:String? = "",var priority:Int? = 0)

