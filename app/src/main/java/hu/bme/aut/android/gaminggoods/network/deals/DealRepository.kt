package hu.bme.aut.android.gaminggoods.network.deals

import hu.bme.aut.android.gaminggoods.domain.model.DealData
import hu.bme.aut.android.gaminggoods.domain.model.Summary

interface DealRepository {
    suspend fun getDeal(id: Int): DealData

    suspend fun getDeals(
        platform: String? = null,
        sortBy: String? = null,
        type: String? = null,
    ): List<DealData>

    suspend fun getWorth(): Summary
}