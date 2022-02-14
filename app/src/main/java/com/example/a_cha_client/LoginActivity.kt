package com.example.a_cha_client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a_cha_client.Auth.auth
import com.example.a_cha_client.Auth.uid
import com.example.a_cha_client.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    companion object{
        var instance:LoginActivity? = null
    }
    val RC_SIGN_IN = 30
    val binding by lazy{ActivityLoginBinding.inflate(layoutInflater)}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        instance = this


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("978100981264-0gu0ids3v4hh20pspek765338n0b3j0k.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)





        Log.d("loginTest",auth.currentUser.toString())

        binding.googleLoginBtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        binding.logOut.setOnClickListener {
            Firebase.auth.signOut()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("loginGoogle", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("loginGoogle", "Google sign in failed", e)
            }
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    uid = auth.uid!!
                    Log.d("loginTest","uid:"+uid.toString())
                    Firebase.firestore.collection("users").document(uid).get().addOnSuccessListener {
                        if(it.data==null){
                            Log.d("loginTest","회원가입 필요")
                            val joinIntent = Intent(this,JoinMemberActivity::class.java)
                            startActivity(joinIntent)
                        }
                        else{
                            Log.d("loginTest","회원정보 확인됨"+it.toString())
                            DataFunction.userInfoData = it.toObject<UserInfoData>()!!
                            val intentGoMain = Intent(this,MainActivity::class.java)
                            startActivity(intentGoMain)
                            finish()
                        }
                    }

                    //DataFunction.join_member()



                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

}