package hu.bme.aut.android.gaminggoods.feature.deals

import hu.bme.aut.android.gaminggoods.model.DealData

interface DealDataArrayHolder {
    fun getDealDataList(): Array<DealData?>?
}