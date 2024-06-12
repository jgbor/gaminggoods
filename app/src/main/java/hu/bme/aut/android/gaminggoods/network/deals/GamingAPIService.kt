package hu.bme.aut.android.gaminggoods.network.deals

import hu.bme.aut.android.gaminggoods.domain.model.DealData
import hu.bme.aut.android.gaminggoods.domain.model.Summary
import retrofit2.http.GET
import retrofit2.http.Query

interface GamingAPIService {
    @GET("/api/giveaways")
    suspend fun getDeals(
        @Query("platform") platform: String?,
        @Query("sort-by") sortBy: String?,
        @Query("type") type: String?
    ): List<DealData>

    @GET("/api/filter")
    suspend fun getDealsWithMultiplePlatforms(
        @Query("platform") platform: String,
        @Query("sort-by") sortBy: String?,
        @Query("type") type: String?
    ): List<DealData>

    @GET("/api/giveaway")
    suspend fun getDeal(
        @Query("id") id: Int
    ): DealData

    @GET("/api/worth")
    suspend fun getWorth(): Summary
}