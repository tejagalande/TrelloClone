package com.example.trelloclone.firebase

import android.util.Log
import com.example.trelloclone.Models.User
import com.example.trelloclone.Utils.Constants
import com.example.trelloclone.activities.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStore {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo : User){

        mFireStore.collection(Constants.USERS).document(getCurrentUserID()).set(userInfo,
            SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener {
                exception ->
                Log.e(activity.javaClass.simpleName,"Error")
            }
    }

    fun getCurrentUserID() : String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}