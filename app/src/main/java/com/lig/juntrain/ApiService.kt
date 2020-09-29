//package com.lig.juntrain
/**
 * Unused
 */
//
//import android.os.Parcel
//import android.os.Parcelable
//import retrofit2.Call
//import retrofit2.Retrofit
//
//private const val BASE_URL = "https://mars.udacity.com/"
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .build()
//
//interface ApiService {
//    @GET("realestate")
//    fun getProperties() : Call<String>
//}
//
//
//object Api {
//    val retrofitService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//
//}
//
//enum class MarsApiFilter(val value: String) {
//    SHOW_RENT("rent") {
//        fun getProperties(@Query("filter") type: String) {
//
//        }
//    },
//    SHOW_BUY("buy") {
//        fun getProperties(@Query("filter") type: String) {
//
//        }
//    },
//    SHOW_ALL("all") {
//        fun getProperties(@Query("filter") type: String) {
//
//        }
//    };
//}
