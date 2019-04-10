package com.nabil.nahla.porter.ui.login.view

import com.nabil.nahla.porter.data.models.ResponseLogin

interface LoginView {
    fun showLoading()

    fun hideLoading()

    fun showMessage(stringResourceId: Int)

    fun showMessage(errorMsg: String)

    fun proceedToNext(response: ResponseLogin)

    fun proceedToNext(token: String)

}