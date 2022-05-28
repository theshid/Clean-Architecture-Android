package com.example.ecomkt.data_remote.interceptors

import com.example.ecomkt.data_remote.AccessToken
import com.example.ecomkt.data_remote.api.ApiInterface
import com.example.ecomkt.app.util.Session
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Route
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val session: Session, private val apiInterface: Provider<ApiInterface>
) : Authenticator {
     private var accessToken: AccessToken ?= null


    private fun refreshToken() :AccessToken?{
        val call = apiInterface.get().getToken("Bearer", "murali", "welcome", "password")
        call.enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                if (response.isSuccessful){
                    if (response.body() != null) {

                        // mPrefs.saveExpiryTime(response.body().expires);

                        accessToken = response.body()!!
                        Timber.d("value of response:" + response.body()!!.token)
                        session.saveToken(accessToken!!.token)
                        session.saveExpiryTime(accessToken!!.expires)
                        Timber.d("value of expiry time converted in calendar:" + accessToken!!.expires)
                    }
                }

            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                Timber.d("value of response" + "failed error" + t.message)
            }

        })
        return accessToken
    }

    override fun authenticate(route: Route?, response: okhttp3.Response): Request {
        Timber.d("response code:${response.code}")
        return runBlocking {
            val newToken = refreshToken()
            response.request.newBuilder()
                .header("Authorization", "Bearer " + session.getUserToken())
                .build()
        }
    }
}