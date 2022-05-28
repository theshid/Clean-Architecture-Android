package com.example.ecomkt.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

internal object NetworkUtils {

    fun getNetworkStatus(context: Context): LiveData<Boolean> {
        val isAvailableLiveData = MutableLiveData<Boolean>()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder()

        cm.registerNetworkCallback(nr.build(), object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isAvailableLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isAvailableLiveData.postValue(false)

            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
            }
        })
        return isAvailableLiveData
    }
}