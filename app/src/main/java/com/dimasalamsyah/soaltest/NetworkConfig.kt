package com.dimasalamsyah.soaltest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

class NetworkConfig {
    // set interceptor
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return  okHttpClient
    }
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.56.1/soaltest/soaltest-api/public/api/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getService() = getRetrofit().create(News::class.java)
}

interface News {
    @GET("barang")
    fun getBarang(): Call<ResponseApi>

    @FormUrlEncoded
    @POST("barang")
    fun postBarang(@Field("kodebarang") kodebarang: String, @Field("namabarang") namabarang: String, @Field("stokbarang") stokbarang: Int, ): Call<ResponseApi>

    @FormUrlEncoded
    @POST("barang/{id}")
    fun updateBarang(@Path("id") id: Int, @Field("kodebarang") kodebarang: String, @Field("namabarang") namabarang: String, @Field("stokbarang") stokbarang: Int, @Field("_method") _method: String): Call<ResponseApi>

    @DELETE("barang/{id}")
    fun deleteBarang(@Path("id") id: Int): Call<ResponseApi>

    @FormUrlEncoded
    @POST("transaksi")
    fun postTransaksi(@Field("details") details: String, @Field("nomortransaksi") nomortransaksi: String, @Field("jenistransaksi") jenistransaksi: String): Call<ResponseApi>

    @GET("transaksi")
    fun getTransaksi(): Call<ResponseTransaksiHeaderApi>

    @GET("transaksidetail/{id}")
    fun getTransaksiDetail(@Path("id") id: Int): Call<ResponseTransaksiDetailApi>

}