package com.twms.twms_f_m_android.util

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.twms.twms_f_m_android.ui.MainActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val context: Context
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getToken().first()
        }

        val cookie = runBlocking {
            tokenManager.getCookie().first()
        }

        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        request.addHeader("Cookie", cookie.toString())

        val response = chain.proceed(request.build())

        val asd = response.headers("Set-Cookie").toString()

        if (response.headers("Set-Cookie").isNotEmpty()) {
            runBlocking { tokenManager.saveCookie(response.headers("Set-Cookie").toString()) }
        }

        if (response.code == 401) {
            runBlocking {
                tokenManager.deleteToken();
                tokenManager.deleteCookie();
            }

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            startActivity(context, intent, Bundle.EMPTY)
        }

        return response
    }

}