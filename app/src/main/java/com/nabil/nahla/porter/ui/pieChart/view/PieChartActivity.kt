package com.nabil.nahla.porter.ui.pieChart.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.nabil.nahla.porter.data.models.ProductsItem
import com.nabil.nahla.porter.ui.barcode.view.BarcodeActivity
import com.nabil.nahla.porter.ui.login.view.LoginActivity
import com.nabil.nahla.porter.ui.pieChart.adapter.DataAdapter
import com.nabil.nahla.porter.ui.pieChart.presenter.PieChartPresenter
import com.nabil.nahla.porter.ui.pieChart.presenter.PieChartPresenterImp
import kotlinx.android.synthetic.main.activity_pie_chart.*
import java.util.*


class PieChartActivity : AppCompatActivity(), PieChartView {
    private lateinit var pieChartPresenter: PieChartPresenter

    private val keyToken = "KEY_TOKEN"

    private var allData: MutableList<ProductsItem> = mutableListOf()
    private val dataAdapter: DataAdapter = DataAdapter(allData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        pieChartPresenter = PieChartPresenterImp(this)

        setPieChartProperties()
        setRecyclerView()

        if (isOnline()) pieChartPresenter.checkTokenExixts(getToken())
        else showInternetSnackBar()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logoutAction -> {
            logout()
            true
        }
        R.id.barcodeAction -> {
            openBarcodeActivity()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        progress_pieChart.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_pieChart.visibility = View.GONE
    }

    override fun showMessage(stringResourceId: Int) {
        showErrorSnackBar(getString(stringResourceId))
    }

    override fun showMessage(errorMsg: String) {
        showErrorSnackBar(errorMsg)
    }

    override fun loadProducts(products: MutableList<ProductsItem>) {
        dataAdapter.updateData(products)
        setPieData(products)
    }

    private fun setPieChartProperties() {
        pieChart.isRotationEnabled = false
        pieChart.isHighlightPerTapEnabled = true
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutBack)
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)

        val l = pieChart.legend
        l.position = RIGHT_OF_CHART
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.isWordWrapEnabled = true
        l.yEntrySpace = 7f
    }

    private fun setRecyclerView() {
        dataRV.isFocusable = false
        containerLL.requestFocus()

        dataRV.isNestedScrollingEnabled = false
        dataRV.layoutManager = LinearLayoutManager(this)

        val mDividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        dataRV.addItemDecoration(mDividerItemDecoration)

        dataRV.adapter = dataAdapter
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    private fun showInternetSnackBar() {
        val snackbar = Snackbar
            .make(findViewById(android.R.id.content), getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.try_again)) {
                if (isOnline()) pieChartPresenter.checkTokenExixts(getToken())
                else showInternetSnackBar()
            }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    private fun getToken(): String {
        val settings = getSharedPreferences("TOKEN", 0)
        val token = settings.getString(keyToken, "")
        Log.d("token= ", token)
        return token ?: throw NullPointerException("Expression 'token' must not be null")
    }

    private fun setPieData(allData: MutableList<ProductsItem>) {
        val entries = ArrayList<PieEntry>()

        var colors: MutableList<Int> = mutableListOf()

        for (i in 0 until allData.size) {
            val item = allData[i].count!!.toFloat()

            entries.add(PieEntry(item, allData[i].name))

            colors.add(Color.parseColor(generateColor(Random())))
        }

        val set = PieDataSet(entries, "")
        set.colors = colors

        pieChart.data = PieData(set)
        pieChart.invalidate()

    }

    private fun generateColor(r: Random): String {
        val hex = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
        val s = CharArray(7)
        var n = r.nextInt(0x1000000)

        s[0] = '#'
        for (i in 1..6) {
            s[i] = hex[n and 0xf]
            n = n shr 4
        }
        return String(s)
    }

    private fun showErrorSnackBar(errorMsg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.logout)) {
                logout()
            }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        clearToken()
        openLoginActivity()
    }

    private fun clearToken() {
        val settings = getSharedPreferences("TOKEN", 0)
        val editor = settings.edit()
        editor.clear()
        editor.apply()
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun openBarcodeActivity() {
        val intent = Intent(this, BarcodeActivity::class.java)
        startActivity(intent)
    }

}
