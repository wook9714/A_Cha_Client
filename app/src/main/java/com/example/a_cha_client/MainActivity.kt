package com.example.a_cha_client


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a_cha_client.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import java.util.*
import android.os.CountDownTimer
import android.view.MenuItem
import com.example.a_cha_client.Auth.uid
import com.example.a_cha_client.calendarDecorator.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.collections.HashMap
import kotlin.time.Duration.Companion.hours
import kotlin.time.hours


class MainActivity : AppCompatActivity() {

    companion object{
        var db = Firebase.firestore
        /*
        var shoppingCartRef = db.collection("testCollection").document(uid).
        collection("testShoppingCart").document("최근장바구니 with 정렬순서")
         */
        var shoppingCartRef = DataFunction.shopping_basket_ref
        var usersShoppingCartForServer = ShoppingListData()
        var usersShoppingCartList = mutableListOf<OrderListData>()
        var loadedMenuData = mutableListOf<MenuItemClass>()
    }

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Functions.makeStatusBarTransparent(window)

        binding.testButton.setOnClickListener {
            val intentToTestActivity = Intent(this, TestActivity :: class.java)
            startActivity(intentToTestActivity)
        }
        //버튼 눌렀을 때 액션
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.orderListOrPayButton.setOnClickListener {
            val intentToOrderList = Intent(this, OrderListActivity :: class.java)
            startActivity(intentToOrderList)
        }

        //var data : MutableList<MenuInformation> = Functions.loadData()
        var adapter = MenuRecyclerAdapter()
        //adapter.listData = data

        binding.menuRecyclerView.adapter = adapter
        val spanCount = 2 // 2 columns for recyclerview
        val spacing = 50 // 50px
        val includeEdge = true

        binding.menuRecyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        binding.menuRecyclerView.layoutManager = GridLayoutManager(this, 2)

        db.collection("items").document("죠샌드위치").collection("메뉴").get().addOnSuccessListener {
            loadedMenuData.clear()
            Log.d("loadTag","menuDataLoaded")
            for(i in it.documents){
                var item = i.toObject<MenuItemClass>()
                loadedMenuData.add(item!!)
            }
            loadedMenuData.sortBy { it.priority }
            adapter.listData = loadedMenuData
            adapter.notifyDataSetChanged()



        }





















        //ca
        getRangeForOrder()
        binding.calendar.state().edit()
            .setMinimumDate(CalendarDay.from(CalendarDay.today().year,firstOrderMonth,firstOrderDay))
            .setMaximumDate(CalendarDay.from(CalendarDay.today().year,lastOrderMonth,lastOrderDay))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()
        //binding.calendar.(CalendarDay.from(CalendarDay.today().year,CalendarDay.today().month,CalendarDay.today().day +1), CalendarDay.from(CalendarDay.today().year,CalendarDay.today().month+1,CalendarDay.today().day))
        binding.calendar.addDecorator(OneDayDecorator())
        //ClosedDay
        binding.calendar.addDecorator(TuesdayDecorator())
        binding.calendar.addDecorator(ThursdayDecorator())
        binding.calendar.addDecorator(SaturdayDecorator())
        binding.calendar.addDecorator(SundayDecorator())
        //OpenedDay
        binding.calendar.addDecorator(MondayDecorator())
        binding.calendar.addDecorator(WednesdayDecorator())
        binding.calendar.addDecorator(FridayDecorator())
        // 기한 표시창
        binding.dateTextView.text = ""
        binding.dayTextView.text = "날짜를 선택해주세요"
        binding.textviewWillOrder.text = ""
        //날짜 선택 로직
        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            val Year = date.year
            val Month = date.month + 1
            val Day = date.day
            var WeekDay : String = "()"
            when (date.date.day){
                0 -> WeekDay = "(일)"
                1 -> WeekDay = "(월)"
                2 -> WeekDay = "(화)"
                3 -> WeekDay = "(수)"
                4 -> WeekDay = "(목)"
                5 -> WeekDay = "(금)"
                6 -> WeekDay = "(토)"
            }
            val Date = (date.month+1).toString()+" 월 "+date.day.toString()+" 일"
            Log.i("Year test", Year.toString() + "")
            Log.i("Month test", Month.toString() + "")
            Log.i("Day test", Day.toString() + "")
            val shot_Day = "$Year,$Month,$Day"
            Log.i("shot_Day test", shot_Day + "")
            binding.dateTextView.text = Date
            binding.dayTextView.text = WeekDay
            binding.textviewWillOrder.text = "에 주문할게요"
            Toast.makeText(applicationContext, shot_Day, Toast.LENGTH_SHORT).show()

            var currentTime : Date = Calendar.getInstance().time
            Log.d(TAG, "onCreate: ${currentTime}, day : ${currentTime.date}, hour : ${currentTime.hours}, minutes : ${currentTime.minutes}, seconds : ${currentTime.seconds}")
            var convertTime = listOf<Int>(currentTime.month, currentTime.date, currentTime.hours, currentTime.minutes, currentTime.seconds)
            var countDownTimer = object : CountDownTimer(200000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var leftTime = Functions.getLeftTime(Year, Month , Day)
                    binding.leftMinuteTextView.text = leftTime[1]
                    binding.leftSecondText.text = leftTime[2]
                }
                override fun onFinish() {
                }
            }
            var leftTime = Functions.getLeftTime(Year, Month , Day)
            binding.leftHourTextView. text = leftTime[0]
            countDownTimer.start()
        }


    }

    override fun onResume() {
        super.onResume()
        Functions.updateShoppingList()
    }



    var firstOrderMonth : Int = 0
    var firstOrderDay : Int = 0

    var lastOrderDay : Int = 0
    var lastOrderMonth : Int = 0
    var hourOfToday : Int = 0

    fun getRangeForOrder(){
        val calendar: Calendar = GregorianCalendar()
        var monthOfToday : Int = CalendarDay.today().month
        var dayOfToday : Int = CalendarDay.today().day
        var lastDateOfMonth : Int
        hourOfToday = calendar[Calendar.HOUR_OF_DAY]


        if(monthOfToday == 1 or 3 or 5 or 7 or 8 or 10 or 12){
            lastDateOfMonth = 31
        }else if(monthOfToday == 2){
            lastDateOfMonth = 28
        }else{
            lastDateOfMonth = 30
        }

        if (dayOfToday == lastDateOfMonth){
            firstOrderDay = 1
            firstOrderMonth = monthOfToday + 1
        }else{
            firstOrderDay = dayOfToday + 1
            firstOrderMonth = monthOfToday
        }

        if (dayOfToday + 21 > lastDateOfMonth){
            lastOrderDay = dayOfToday + 21 - lastDateOfMonth
            lastOrderMonth = monthOfToday + 1
        }else{
            lastOrderDay = dayOfToday +21
            lastOrderMonth = monthOfToday
        }
        Log.d(TAG, "getRangeForOrder: 첫날 :${firstOrderMonth+1} 월 ${firstOrderDay} 일 현제시각 : ${hourOfToday}")

        if(hourOfToday>=23){
            if(firstOrderDay == lastDateOfMonth){
                firstOrderDay = 1
                firstOrderMonth += 1
            }else{
                firstOrderDay +=  1
            }
        }
        Log.d(TAG, "getRangeForOrder: 첫날 :${firstOrderMonth+1} 월 ${firstOrderDay} 일 현제시각 : ${hourOfToday}")
    }


    fun addMenu(){
        var item = MenuItemClass()
        item.name = "치킨샐러드"
        item.description = "신선한 야채와 닭가슴살이 만나 부담없이 즐길 수 있는 샐러드"
        item.priority = 7


        item.price = 5000



        item.imageLink = item.name+"메뉴이미지"

        db.collection("items").document("죠샌드위치").collection("메뉴").document(item.name!!).set(item)
    }













}


