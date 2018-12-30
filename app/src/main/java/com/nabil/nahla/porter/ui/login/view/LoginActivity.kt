package com.nabil.nahla.porter.ui.login.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.nabil.nahla.porter.R
import com.nabil.nahla.porter.data.models.ResponseLogin
import com.nabil.nahla.porter.ui.login.presenter.LoginPresenter
import com.nabil.nahla.porter.ui.login.presenter.LoginPresenterImp
import com.nabil.nahla.porter.ui.pieChart.view.PieChartActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var loginPresenter: LoginPresenter
    private val keyToken = "KEY_TOKEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenterImp(this)

        signInB.setOnClickListener {
            if (isOnline()) validateEmailPassword()
            else showInternetSnackBar()
        }
    }

    override fun showLoading() {
        progress_login.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_login.visibility = View.GONE
    }

    override fun showMessage(stringResourceId: Int) {
        showErrorSnackBar(getString(stringResourceId))
    }

    override fun showMessage(errorMsg: String) {
        showErrorSnackBar(errorMsg)
    }

    override fun proceedToNext(response: ResponseLogin) {
        saveToken(response.token ?: throw NullPointerException("Expression 'response?.token' must not be null"))
        openDataActivity()
    }

    private fun validateEmailPassword() {
        loginPresenter.validateData(
            (emailET?.text ?: throw NullPointerException("Expression 'emailET?.text' must not be null")).toString()
            ,
            (passwordET?.text
                ?: throw NullPointerException("Expression 'passwordET?.text' must not be null")).toString()
        )
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun showInternetSnackBar() {
        val snackbar = Snackbar
            .make(findViewById(android.R.id.content), getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.try_again)) {
                if (isOnline()) validateEmailPassword()
                else showInternetSnackBar()
            }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    fun showErrorSnackBar(errorMsg: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            errorMsg,
            Snackbar.LENGTH_LONG
        )
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    private fun saveToken(token: String) {

        val settings = getSharedPreferences("TOKEN", 0)
        val editor = settings.edit()
        editor.putString(keyToken, token)
        editor.apply()

    }

    private fun openDataActivity() {
        val intent = Intent(this, PieChartActivity::class.java)
        startActivity(intent)
    }

}
