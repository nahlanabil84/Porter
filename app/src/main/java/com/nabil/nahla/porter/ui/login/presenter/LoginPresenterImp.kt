package com.nabil.nahla.porter.ui.login.presenter

import com.nabil.nahla.porter.data.models.ResponseLogin
import com.nabil.nahla.porter.ui.login.model.LoginModel
import com.nabil.nahla.porter.ui.login.model.LoginModel.OnFireBaseLoginFinishedListener
import com.nabil.nahla.porter.ui.login.model.LoginModel.OnLoginFinishedListener
import com.nabil.nahla.porter.ui.login.model.LoginModelImp
import com.nabil.nahla.porter.ui.login.view.LoginView
import java.util.regex.Pattern

class LoginPresenterImp(private val loginView: LoginView) : LoginPresenter, OnFireBaseLoginFinishedListener, OnLoginFinishedListener {
    private val loginModel: LoginModel

    init {
        this.loginModel = object : LoginModelImp() {}
    }

    override fun validateData(email: String, password: String) {
        //check email and password
        if (isEmailValid(email) && password.length > 5) {
            loginView.showLoading()
           // loginModel.postLogin(email, password, this)
            loginModel.postLoginViaFireBase(email, password, this)

        } else if (!isEmailValid(email)) {
            loginView.showMessage(R.string.error_invalid_email)

        } else if (password.length < 6) {
            loginView.showMessage(R.string.error_invalid_password)

        } else loginView.showMessage(R.string.error_invalid_email_or_password)
    }

    override fun onFailed(stringResourceId: Int) {
        loginView.hideLoading()
        loginView.showMessage(stringResourceId)
    }

    override fun onFailed(errorMsg: String) {
        loginView.hideLoading()
        loginView.showMessage(errorMsg)
    }

    override fun onSuccess(response: ResponseLogin) {
        loginView.hideLoading()
        loginView.proceedToNext(response)
    }

    override fun onSuccess(token: String) {
        loginView.hideLoading()
        loginView.proceedToNext(token)
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
