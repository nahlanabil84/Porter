package com.nabil.nahla.porter.ui.login.model

import com.google.firebase.database.*
import com.nabil.nahla.porter.data.models.ProductsItem


open class PieChartModelImp : PieChartModel {
    override fun getTestingDataViaFireBase(token: String, listener: PieChartModel.OnDataLoadedListener) {

        val myRef = FirebaseDatabase.getInstance().getReference("products")
        myRef.keepSynced(true)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val t = object : GenericTypeIndicator<MutableList<ProductsItem>>() {

                }
                val responseProducts = dataSnapshot.getValue(t)
                listener.onSuccess(responseProducts!!)
            }

            override fun onCancelled(error: DatabaseError) {
                listener.onFailed(error.toException().localizedMessage.toString())
            }
        })
    }
}