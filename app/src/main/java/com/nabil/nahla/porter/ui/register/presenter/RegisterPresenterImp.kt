package com.nabil.nahla.porter.ui.register.presenter

import com.nabil.nahla.porter.ui.register.model.RegisterModel
import com.nabil.nahla.porter.ui.register.model.RegisterModel.OnFireBaseRegisterFinishedListener
import com.nabil.nahla.porter.ui.register.model.RegisterModelImp
import com.nabil.nahla.porter.ui.register.view.RegisterView
import java.util.regex.Pattern


class RegisterPresenterImp( private val registerView: RegisterView): RegisterPresenter, OnFireBaseRegisterFinishedListener{
    private val registerModel: RegisterModel

    init {
        this.registerModel = object : RegisterModelImp() {}
    }


    override fun onFailed(errorMsg: String) {
        registerView.hideLoading()
        registerView.showMessage(errorMsg)
    }

    override fun onSuccess(token: String) {
        registerView.hideLoading()
        registerView.proceedToNext(token)
    }

    override fun validateData(email: String, password: String, confirmPassword: String) {
        //check email and password
        if (isEmailValid(email) && password.length > 5 && confirmPassword.equals(password)) {
            registerView.showLoading()
            registerModel.postRegisterViaFireBase(email, password, this)

        } else if (!isEmailValid(email)) {
            registerView.showMessage(R.string.error_invalid_email)

        } else if (password.length < 6) {
            registerView.showMessage(R.string.error_invalid_password)

        } else if (!confirmPassword.equals(password)) {
            registerView.showMessage(R.string.error_invalid_confirm_password)

        } else registerView.showMessage(R.string.error_invalid_email_or_password)
    }

    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

}