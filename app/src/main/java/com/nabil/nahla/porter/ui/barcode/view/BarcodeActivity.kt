package com.nabil.nahla.porter.ui.barcode.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.nabil.nahla.porter.R
import kotlinx.android.synthetic.main.activity_barcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class BarcodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, PermissionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            checkPermission()
        } else{
            showAllowedPermissionSB()
        }

    }

    private fun checkPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(this)
            .check()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        showAllowedPermissionSB()
    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
        token?.continuePermissionRequest()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        showDeniedPermissionSB()
    }

    override fun onPause() {
        super.onPause()
        zxingSV.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        zxingSV.setResultHandler(this)
        zxingSV.flash = true
        zxingSV.setAutoFocus(true)
        zxingSV.setAspectTolerance(0.5f)
        zxingSV.startCamera()
    }

    override fun handleResult(p0: Result?) {
        Log.v("Result Text", p0?.text)
        barcodeInfoTV.text = p0?.text
        zxingSV.resumeCameraPreview(this)
    }

    private fun showDeniedPermissionSB() {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_permission_required), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.apply_permission)) {
                checkPermission()
            }
        snackbar.setActionTextColor(Color.WHITE)
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.RED)
        snackbar.show()
    }

    private fun showAllowedPermissionSB() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.permission_allowed),
            Snackbar.LENGTH_LONG
        )
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        textView.setTextColor(Color.GREEN)
        snackbar.show()
    }

}
