package com.bayuirfan.madesubmission.model.remote

import com.bayuirfan.madesubmission.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieCatalougeService {
    companion object {
        private val builder = OkHttpClient.Builder()
        fun getClient(): MovieCatalougeInterface{
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(builder.build())
                    .build()
            return retrofit.create(MovieCatalougeInterface::class.java)
        }
    }
}