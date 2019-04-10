package com.nabil.nahla.porter.ui.login.model

import com.google.firebase.auth.FirebaseAuth
import com.nabil.nahla.porter.data.models.ResponseLogin
import com.nabil.nahla.porter.data.network.AppApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class LoginModelImp : LoginModel {

    override fun postLoginViaFireBase(email: String, password: String, listener: LoginModel.OnFireBaseLoginFinishedListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                listener.onSuccess(FirebaseAuth.getInstance().currentUser!!.uid)
            } else {
                listener.onFailed(task.exception!!.localizedMessage.toString())
            }
        }
    }

    override fun postLogin(email: String, password: String, listener: LoginModel.OnLoginFinishedListener) {
        AppApiHelper("").api()?.postCustomerLogin(email, password)?.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    if (!response.body()?.token.isNullOrEmpty() && response.body()?.error.isNullOrEmpty())
                        listener.onSuccess(response.body()!!)
                    else if (!response.body()?.error.isNullOrEmpty())
                        listener.onFailed(response.body()?.error!!)
                } else {
                    listener.onFailed(response.message().toString())
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }
}