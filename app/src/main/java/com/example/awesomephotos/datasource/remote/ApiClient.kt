package com.example.awesomephotos.datasource.remote

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.unsplash.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
        }

        /**
         * @param timeout TimeOut in seconds
         */
        private fun getOkHttpClient(timeout: Int = 120): OkHttpClient {

            val builder = OkHttpClient.Builder()
            val dispatcher = Dispatcher()
            builder.dispatcher(dispatcher)

            builder.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)

            builder.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Client-ID N_sLL516R-J1Lb91_rkJshfo2wJjznxO5SjtdCwV9Q8")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }

            return builder.build()
        }

    }
}