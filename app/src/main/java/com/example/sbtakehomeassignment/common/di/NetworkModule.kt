package com.example.sbtakehomeassignment.common.di


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).dispatcher(dispatcher).connectTimeout(
            NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .callTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS).build()

    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun providesRetrofitBuilder(
        httpClient: OkHttpClient
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
        }

        val contentType = "application/json".toMediaType()
        // For simplicity in this assignment we are passing baseUrl directly here but base Url can be fetch from local.properties which is more secure way.
        return Retrofit.Builder().baseUrl("https://api.github.com").client(httpClient)
            .addConverterFactory(json.asConverterFactory(contentType)).build()
    }


}

const val NETWORK_TIMEOUT = 30000L



