package com.example.personalcoach.data.network.interceptor

import android.text.TextUtils
import com.example.personalcoach.domain.pref.AuthenticationPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderTokenInterceptor(
    private val prefs: AuthenticationPreferences
) : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return if(TextUtils.isEmpty(prefs.getToken())){
            chain.proceed(original)
        }
        else{
            val request = original.newBuilder()
                .header("Authorization", prefs.getToken())
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
    }

}