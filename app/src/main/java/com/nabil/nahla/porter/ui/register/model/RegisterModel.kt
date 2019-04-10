package com.nabil.nahla.porter.ui.register.model

interface RegisterModel {

    interface OnFireBaseRegisterFinishedListener {
        fun onFailed(errorMsg: String)

        fun onSuccess(token: String)
    }

    fun postRegisterViaFireBase(email: String, password: String, listener: OnFireBaseRegisterFinishedListener)

}