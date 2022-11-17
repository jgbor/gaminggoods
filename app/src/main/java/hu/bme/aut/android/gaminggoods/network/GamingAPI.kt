package hu.bme.aut.android.gaminggoods.network

import hu.bme.aut.android.gaminggoods.model.DealData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GamingAPI {
    @GET("/api/giveaways")
    fun getDeals(
        @Query("platform") platform: String?,
        @Query("sort-by") sortBy: String?,
        @Query("type") type: String?
    ): Call<Array<DealData?>?>?
}