package com.example.a_cha_client

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class MenuInformation(var name : String, var price : Int)

data class OrderListData(var menuName : String? = null, var quantity : Int? = null)

data class TestDataClass(
    var shoppingCart: MutableMap<String,Int>? = null
)

data class ShoppingListData(
    var shoppingListArray: MutableList<MutableMap<String, Int>?>? = null
)

data class UserInfoData(
    var name:String?="",
    var detailedLocation:String? = "",
    var geoLocaion:GeoPoint? = null,
    var orderIDs:MutableList<String>? = mutableListOf(),
    var phoneNumber:String? = "",
    var subscription:Boolean? = false
)


//업체 메뉴에 대한 데이터 클래스
data class MenuItemClass(var name:String? = "", var price:Int? = 0,var description:String? = "", var imageLink:String? = "",var priority:Int? = 0)



//아차 딜리버리와 연동되는 데이터 클래스
data class OrderInfo(
    var deliveryState:Int =0,
    var deliveryTime: Timestamp = Timestamp(0,0),
    var detailedLocation:String="",
    var geoLocation:GeoPoint=GeoPoint(0.0,0.0),
    var orderItems:String? = "",
    var userID:String = "",
    var userNeed:String = ""
)


//수정한 데이터 클래스들

//json로드의 근간이 되는 데이터 클래스
data class JsonStringData(
    var jsonData:String?=""
)

data class BillingInfo(
    var orderedItems:MutableList<MenuInfo>? = mutableListOf()
)

data class BasketData(
    var orderedItems:MutableList<Pair<MenuInfo,Int>>? = null
)

data class MenuInfo(
    var name:String? = "",
    var storeName:String? = "",
    var price:Int? = 0,
    var description:String? = "",
    var imageLink:String? = "",
    var priority:Int? = 0
)