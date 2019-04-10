package com.nabil.nahla.porter.ui.register.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.nabil.nahla.porter.ui.pieChart.view.PieChartActivity
import com.nabil.nahla.porter.ui.register.presenter.RegisterPresenter
import com.nabil.nahla.porter.ui.register.presenter.RegisterPresenterImp
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var registerPresenter: RegisterPresenter
    private val keyToken = "KEY_TOKEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerPresenter = RegisterPresenterImp(this)

        SignUpB.setOnClickListener {
            if (isOnline()) validateEmailPassword()
            else showInternetSnackBar()
        }

        alreadyHaveAccTV.setOnClickListener {
            finish()
        }


    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    private fun validateEmailPassword() {
        registerPresenter.validateData(
            (emailET?.text ?: throw NullPointerException("Expression 'emailET?.text' must not be null")).toString()
            ,
            (passwordET?.text
                ?: throw NullPointerException("Expression 'passwordET?.text' must not be null")).toString()
            ,
            (confirmPasswordET?.text
                ?: throw NullPointerException("Expression 'passwordET?.text' must not be null")).toString()
        )
    }

    private fun showInternetSnackBar() {
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

    private fun showErrorSnackBar(errorMsg: String) {
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
        if (!getToken().isEmpty()) {
            val intent = Intent(this, PieChartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun getToken(): String {
        val settings = getSharedPreferences("TOKEN", 0)
        val token = settings.getString(keyToken, "")
        Log.d("token= ", token)
        return token ?: throw NullPointerException("Expression 'token' must not be null")
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

    override fun proceedToNext(token: String) {
        saveToken(token)
        openDataActivity()
    }
}
