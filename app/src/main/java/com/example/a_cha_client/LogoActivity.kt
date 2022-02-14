package com.example.a_cha_client

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        Auth.auth = Firebase.auth

        moveMain(1)



    }

    private fun moveMain(sec: Int) {
        Handler().postDelayed({
            //new Intent(현재 context, 이동할 activity)

            if(Auth.auth.currentUser!=null){
                //auth 정보 있는 경우 mainActivity 자동 실행
                Auth.uid = Auth.auth.uid!!
                Log.d("userInfo","haveUserAuth"+ Auth.auth.currentUser!!.uid)
                DataFunction.db.collection("users").document(Auth.uid).get().addOnSuccessListener {
                    if(it.data !=null){
                        DataFunction.userInfoData = it.toObject<UserInfoData>()!!
                        val goMainActivity = Intent(this,MainActivity::class.java)
                        startActivity(goMainActivity)
                    }
                    else{
                        val goLoginActivity = Intent(this,LoginActivity::class.java)
                        startActivity(goLoginActivity)
                    }
                }

            }
            else{
                val goLoginActivity = Intent(this,LoginActivity::class.java)
                startActivity(goLoginActivity)
            }

            //현재 액티비티 종료
        }, (1000 * sec).toLong()) // sec초 정도 딜레이를 준 후 시작
    }
}