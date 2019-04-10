package com.nabil.nahla.porter.ui.register.view

interface RegisterView{
    fun showLoading()

    fun hideLoading()

    fun showMessage(stringResourceId: Int)

    fun showMessage(errorMsg: String)

    fun proceedToNext(token: String)

}