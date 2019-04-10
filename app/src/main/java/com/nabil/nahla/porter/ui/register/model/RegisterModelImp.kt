package com.nabil.nahla.porter.ui.register.model

import com.google.firebase.auth.FirebaseAuth

open class RegisterModelImp: RegisterModel {
    override fun postRegisterViaFireBase(email: String, password: String, listener: RegisterModel.OnFireBaseRegisterFinishedListener) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    listener.onSuccess(FirebaseAuth.getInstance().currentUser!!.uid)
                } else {
                    listener.onFailed(task.exception!!.localizedMessage.toString())
                }
        }
    }
}