package ru.chatan.app

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkManager {

    private var networkState = true
    private var connectivityManager: ConnectivityManager? = null

    fun getNetworkState() = networkState

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            networkState = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            networkState = false
        }
    }

    fun connect(
        activity: MainActivity
    ) {
        connectivityManager = activity.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager?.requestNetwork(networkRequest, networkCallback)
    }

    fun stop() {
        connectivityManager = null
    }

}