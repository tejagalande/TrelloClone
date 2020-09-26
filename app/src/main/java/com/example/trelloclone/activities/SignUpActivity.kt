package com.example.trelloclone.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.trelloclone.Models.User
import com.example.trelloclone.R
import com.example.trelloclone.firebase.FireStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setUpActionBar()
        btn_sign_up.setOnClickListener { registerUser() }

    }

    fun userRegisteredSuccess(){
        Toast.makeText(this,"You have successfully registered.",Toast.LENGTH_SHORT).show()
        hideProgressDialog()
          FirebaseAuth.getInstance().signOut()
          finish()
    }

    private fun setUpActionBar(){
        setSupportActionBar(toolbar_sign_up)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_button)
        }

        toolbar_sign_up.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun registerUser(){
        val name:String = et_name.text.toString().trim {it <= ' '}
        val email:String = et_email.text.toString().trim {it <= ' '}
        val password:String = et_password.text.toString().trim {it <= ' '}

        if (validateForm(name,email,password)){
            showProgressDialog("Please Wait")
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                    if (task.isSuccessful){
                        val firebaseUser : FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        

                        val user = User(firebaseUser.uid,name,registeredEmail)
                        FireStore().registerUser(this,user)
                    }else{
                        Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(name:String, email:String, password:String):Boolean{
        return when{
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter your name.")
                false
            }

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