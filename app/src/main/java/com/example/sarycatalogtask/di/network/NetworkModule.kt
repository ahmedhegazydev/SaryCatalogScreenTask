package com.example.sarycatalogtask.di.network

import android.content.Context
import com.example.sarycatalogtask.BuildConfig
import com.example.sarycatalogtask.utils.network.NetworkUtil
import com.example.sarycatalogtask.utils.network.NoConnectivityException
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module()
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttpCache(
        @ApplicationContext
        application: Context
    ): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthenticationInterceptor,
        connectivityInterceptor: ConnectivityInterceptor,
        chuckInterceptor: ChuckInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(chuckInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesChuckInterceptor(@ApplicationContext context: Context): ChuckInterceptor {
        return ChuckInterceptor(context).showNotification(true)
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG)
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        else
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.NONE
            }
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    @Singleton
    class AuthenticationInterceptor @Inject constructor(
//        private var sharedPref: SharedPreferences
    ) : Interceptor {
        private var authToken: String? = null

        init {
        }

        fun setAuthToken(authToken: String?) {
            this.authToken = authToken
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            builder.addHeader("Accept-Encoding", "gzip, deflate, br")
            builder.addHeader("Accept", "*/*")
            builder.addHeader("App-Version", "3.1.1.0.0")
            builder.addHeader("Device-Type", "android")
            builder.addHeader("Accept-Language", "ar")
            builder.addHeader("Content-Type", "application/json")
            builder.addHeader("Authorization", "token ${BuildConfig.AUTH_BEARER_TOKEN}")
            request = builder.build()
            return chain.proceed(request)
        }

        override fun equals(other: Any?): Boolean {
            return (other is AuthenticationInterceptor
                    && authToken!!.contentEquals(other.authToken!!))
        }

        override fun hashCode(): Int {
            return authToken?.hashCode() ?: 0
        }
    }


    @Singleton
    @Provides
    fun provideConnectivityInterceptor(
        @ApplicationContext
        context: Context
    ): Interceptor {
        return ConnectivityInterceptor(context)
    }

    @Singleton
    class ConnectivityInterceptor @Inject constructor(
        @ApplicationContext
        private val
        context: Context
    ) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!NetworkUtil.isInternetAvailable(context)) {
                throw NoConnectivityException()
            }
            val builder: Request.Builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }

    }


}
