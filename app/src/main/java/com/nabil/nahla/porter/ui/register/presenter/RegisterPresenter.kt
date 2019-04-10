package com.nabil.nahla.porter.ui.register.presenter

interface RegisterPresenter {
    fun validateData(email: String, password: String, confirmPassword: String)
}