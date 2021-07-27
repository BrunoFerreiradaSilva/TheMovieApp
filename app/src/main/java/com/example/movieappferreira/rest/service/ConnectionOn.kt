package com.example.movieappferreira.rest.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class ConnectionOn {
    fun isConnected(cont: Context): Boolean {
        val cm = cont.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}