package com.example.a_cha_client

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.firebase.firestore.ktx.toObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.ArrayList




val TAG : String = "로그"

object Functions {
    fun makeStatusBarTransparent( window : Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window?.decorView?.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE  or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                } else {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                statusBarColor = Color.TRANSPARENT
            }
        }
        Log.d(TAG,"UIFunction - makeStatusBarTransparent() called")
    }

    fun updateShoppingList(){
        MainActivity.usersShoppingCartByMap.shoppingCart?.clear()
        MainActivity.usersShoppingCart.clear()
        Log.d(TAG, "cleanShoppingCartByMap ${MainActivity.usersShoppingCartByMap}")
        Log.d(TAG, "cleanShoppingCart ${MainActivity.usersShoppingCart}")

        MainActivity.shoppingCartRef.get().addOnSuccessListener {
            val data = it.toObject<TestDataClass>()
            for(i in data!!.shoppingCart!!){
                var menuName = i.key
                var quantity = i.value
                MainActivity.usersShoppingCart.add(OrderListData(menuName, quantity))
            }
            MainActivity.usersShoppingCartByMap = data
            Log.d(TAG, "update shoppingCartByMap ${MainActivity.usersShoppingCartByMap}")
            Log.d(TAG, "update shoppingCart ${MainActivity.usersShoppingCart}")

        }
    }



    fun loadDataFromServer(){
         MainActivity.db.collection("items").document("죠샌드위치").collection("메뉴").get().addOnSuccessListener {
            var a = it
             Log.d(TAG, "loadDataFromServer: ${a.documents}")
             a.query
            
        }
        



    }

    fun loadData() : MutableList<MenuInformation>{
        val data : MutableList<MenuInformation> = mutableListOf()
        var menu1 = MenuInformation("햄치즈샌드위치",6100)
        var menu2 = MenuInformation("클럽샌드위치",6100)
        var menu3 = MenuInformation("데리야키 샌드위치",6100)
        var menu4 = MenuInformation("과일한컵",3200)
        var menu5 = MenuInformation("연어 아보카도 샐러드",8900)
        var menu6 = MenuInformation("레몬에이드",3500)
        data.add(menu1)
        data.add(menu2)
        data.add(menu3)
        data.add(menu4)
        data.add(menu5)
        data.add(menu6)
        return data
    }

    fun convertTimestampToDate(timestamp: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm")
        val date = sdf.format(timestamp)
        Log.d("TTT UNix Date -> ", sdf.format((System.currentTimeMillis())).toString())
        Log.d("TTTT date -> ", date.toString())
    }

    fun getLeftTime(desiredYear : Int, desiredMonth : Int, desiredDay : Int ) : List<String> {
        val date = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val c_hour = calendar[Calendar.HOUR_OF_DAY]
        val c_min = calendar[Calendar.MINUTE]
        val c_sec = calendar[Calendar.SECOND]
        val baseCal: Calendar = GregorianCalendar(year, month, day, c_hour, c_min, c_sec)
        val targetCal: Calendar = GregorianCalendar(desiredYear, desiredMonth-1, desiredDay -1, 23, 0, 0) //비교대상날짜
        val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
        val diffDays = diffSec / (24 * 60 * 60)
        targetCal.add(Calendar.DAY_OF_MONTH, (-diffDays).toInt())
        val hourTime = Math.floor((diffSec / 3600).toDouble()).toInt()
        val minTime =
            Math.floor(((diffSec - 3600 * hourTime) / 60).toDouble()).toInt()
        val secTime =
            Math.floor((diffSec - 3600 * hourTime - 60 * minTime).toDouble()).toInt()
        val hour = String.format("%02d", hourTime)
        val min = String.format("%02d", minTime)
        val sec = String.format("%02d", secTime)
        return listOf(hour, min, sec)
    }
}