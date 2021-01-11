package com.yazidal.covidindonesia.network

import com.yazidal.covidindonesia.model.ResponseProvinsi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("provinsi")
    fun getAllProvinsi(): retrofit2.Call<ResponseProvinsi>
}
interface intoService{
    @GET
    fun getIntoService(@Url url: String)
}
object RetrofitBuilder{
    private val okhttp = OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(11, TimeUnit.SECONDS)
        .readTimeout(11, TimeUnit.SECONDS)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://indonesia-covid-19.mathdro.id/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp)
        .build()
}