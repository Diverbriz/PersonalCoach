package com.example.personalcoach.di.module

import com.example.personalcoach.data.network.ApiService
import com.example.personalcoach.data.network.interceptor.HeaderTokenInterceptor
import com.example.personalcoach.domain.pref.AuthenticationPreferences
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val requestTimeOutTime: Long = 40//20

    @Provides
    fun providesBaseUrl() : String = ApiService.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideClientBuilder(prefs: AuthenticationPreferences): OkHttpClient{
        val builder = OkHttpClient.Builder()

        builder.readTimeout(requestTimeOutTime, TimeUnit.SECONDS)
            .writeTimeout(requestTimeOutTime, TimeUnit.SECONDS)
            .connectTimeout(requestTimeOutTime, TimeUnit.SECONDS)

        builder.addInterceptor(HeaderTokenInterceptor(prefs))

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        return builder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
            val DATE_FORMATS = arrayOf(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'").apply{
                    timeZone = TimeZone.getTimeZone("UTC")
                },
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                SimpleDateFormat("yyyy-MM-dd")
            )
            @Throws(JsonParseException::class)
            override fun deserialize(
                json: JsonElement, typeOfT: Type,
                context: JsonDeserializationContext
            ): Date {
                for(format in DATE_FORMATS) {
                    try {
                        return format.parse(json.asString)
                    } catch (e: ParseException) {}
                }
                throw JsonParseException("Unparseable date: \"" + json.asString
                        + "\". Supported formats: " + DATE_FORMATS.contentToString()
                )
            }
        })
            .serializeNulls()
            .create()
    }

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}