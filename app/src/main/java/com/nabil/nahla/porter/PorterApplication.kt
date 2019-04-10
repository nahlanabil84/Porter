package com.nabil.nahla.porter

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class PorterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }


}