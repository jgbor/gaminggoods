package hu.bme.aut.android.gaminggoods.network

import hu.bme.aut.android.gaminggoods.model.DealData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val gamingAPI: GamingAPI

    private const val SERVICE_URL ="https://www.gamerpower.com"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gamingAPI = retrofit.create(GamingAPI::class.java)
    }

    fun getDeals(platform: String?,sortBy: String?, type: String?): Call<Array<DealData?>?>?{
        return gamingAPI.getDeals(platform,sortBy,type)
    }
}