package com.twms.twms_f_m_android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.twms.twms_f_m_android.BuildConfig
import com.twms.twms_f_m_android.data.network.AuthApi
import com.twms.twms_f_m_android.data.network.InboundShipmentApi
import com.twms.twms_f_m_android.data.network.PutawayApi
import com.twms.twms_f_m_android.data.network.ReceiveApi
import com.twms.twms_f_m_android.data.network.ReceivingApi
import com.twms.twms_f_m_android.repository.AuthRepository
import com.twms.twms_f_m_android.repository.AuthRepositoryImpl
import com.twms.twms_f_m_android.repository.InboundShipmentRepository
import com.twms.twms_f_m_android.repository.InboundShipmentRepositoryImpl
import com.twms.twms_f_m_android.repository.PutawayRepository
import com.twms.twms_f_m_android.repository.PutawayRepositoryImpl
import com.twms.twms_f_m_android.repository.ReceiveRepository
import com.twms.twms_f_m_android.repository.ReceiveRepositoryImpl
import com.twms.twms_f_m_android.repository.ReceivingRepository
import com.twms.twms_f_m_android.repository.ReceivingRepositoryImpl
import com.twms.twms_f_m_android.ui.inbound_shipment.InboundShipmentAdapter
import com.twms.twms_f_m_android.ui.putaway.PutawayAdapter
import com.twms.twms_f_m_android.ui.receiving.ReceivingAdapter
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
    fun provideApiService(retrofit: Retrofit): AuthApi {
        return retrofit
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authApi: AuthApi
    ) = AuthRepositoryImpl(authApi) as AuthRepository

    @Singleton
    @Provides
    fun providePutawayApiService(retrofit: Retrofit): PutawayApi {
        return retrofit
            .create(PutawayApi::class.java)
    }

    @Singleton
    @Provides
    fun providePutawayRepository(
        putawayApi: PutawayApi
    ) = PutawayRepositoryImpl(putawayApi) as PutawayRepository

    @Provides
    @Singleton
    fun providePutawayAdapter(): PutawayAdapter = PutawayAdapter()

    @Singleton
    @Provides
    fun provideInboundShipmentApiService(retrofit: Retrofit): InboundShipmentApi {
        return retrofit
            .create(InboundShipmentApi::class.java)
    }

    @Singleton
    @Provides
    fun provideInboundShipmentRepository(
        inboundShipmentApi: InboundShipmentApi
    ) = InboundShipmentRepositoryImpl(inboundShipmentApi) as InboundShipmentRepository

    @Provides
    @Singleton
    fun provideInboundShipmentAdapter(): InboundShipmentAdapter = InboundShipmentAdapter()

    @Singleton
    @Provides
    fun provideReceiveApiService(retrofit: Retrofit): ReceiveApi {
        return retrofit
            .create(ReceiveApi::class.java)
    }

    @Singleton
    @Provides
    fun provideReceiveRepository(
        receiveApi: ReceiveApi
    ) = ReceiveRepositoryImpl(receiveApi) as ReceiveRepository

    @Singleton
    @Provides
    fun provideReceivingApiService(retrofit: Retrofit): ReceivingApi {
        return retrofit
            .create(ReceivingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideReceivingRepository(
        receivingApi: ReceivingApi
    ) = ReceivingRepositoryImpl(receivingApi) as ReceivingRepository

    @Provides
    @Singleton
    fun provideReceivingAdapter(): ReceivingAdapter = ReceivingAdapter()

}