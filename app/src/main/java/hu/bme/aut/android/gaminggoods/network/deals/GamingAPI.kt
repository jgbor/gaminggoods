package hu.bme.aut.android.gaminggoods.network.deals

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hu.bme.aut.android.gaminggoods.domain.model.DealData
import hu.bme.aut.android.gaminggoods.domain.model.Summary
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
class GamingAPI : DealRepository {

    private val SERVICE_URL = "https://www.gamerpower.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(SERVICE_URL)
        .build()

    private val retrofitService: GamingAPIService by lazy {
        retrofit.create(GamingAPIService::class.java)
    }

    override suspend fun getDeal(id: Int): DealData {
        return retrofitService.getDeal(id)
    }

    override suspend fun getDeals(
        platform: String?,
        sortBy: String?,
        type: String?
    ): List<DealData> {
        var p = platform
        if (platform == "")
            p = null
        else if (platform?.contains(".") == true){
            return retrofitService.getDealsWithMultiplePlatforms(platform, sortBy, type)
        }
        var s = sortBy
        if (sortBy == "")
            s = null
        var t = type
        if (type == "")
            t = null
        return retrofitService.getDeals(p, s, t)
    }

    override suspend fun getWorth(): Summary {
        return retrofitService.getWorth()
    }
}