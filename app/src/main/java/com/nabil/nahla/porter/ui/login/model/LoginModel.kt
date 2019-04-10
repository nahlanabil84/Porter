package com.nabil.nahla.porter.ui.login.model

import com.nabil.nahla.porter.data.models.ResponseLogin

interface LoginModel {
    interface OnLoginFinishedListener {
        fun onFailed(stringResourceId: Int)

        fun onFailed(errorMsg: String)

        fun onSuccess(response: ResponseLogin)
    }

    interface OnFireBaseLoginFinishedListener {
        fun onFailed(errorMsg: String)

        fun onSuccess(token: String)
    }

    fun postLogin(email: String, password: String, listener: OnLoginFinishedListener)

    fun postLoginViaFireBase(email: String, password: String, listener: OnFireBaseLoginFinishedListener)

}