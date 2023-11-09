package com.twms.twms_f_m_android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.twms.twms_f_m_android.BuildConfig
import com.twms.twms_f_m_android.data.network.Api
import com.twms.twms_f_m_android.repository.AuthRepository
import com.twms.twms_f_m_android.repository.AuthRepositoryImpl
import com.twms.twms_f_m_android.util.AuthInterceptor
import com.twms.twms_f_m_android.util.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager, @ApplicationContext context: Context): AuthInterceptor =
        AuthInterceptor(tokenManager, context)
    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) = run {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Api {
        return retrofit
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: Api
    ) = AuthRepositoryImpl(api) as AuthRepository
}