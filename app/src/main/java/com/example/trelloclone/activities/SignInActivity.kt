package com.example.trelloclone.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.example.trelloclone.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        btn_sign_in.setOnClickListener {
            signInRegisteredUser()
        }
        setUpActionBar()

    }

    private fun setUpActionBar(){
        setSupportActionBar(toolbar_sign_in)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_button)
        }

        toolbar_sign_in.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun signInRegisteredUser(){
        val email : String = et_sign_in_email.text.toString().trim {it <= ' '}
        val password : String = et_sign_in_password.text.toString().trim {it <= ' '}

        if (validateForm(email,password)){
            showProgressDialog("please wait")
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("sign in", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this,MainActivity::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
            }

    }

    private fun validateForm(email:String, password:String):Boolean{
        return when{

            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter your email id.")
                false
            }

            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter your password.")
                false
            }
            else ->{true}
        }
    }


}