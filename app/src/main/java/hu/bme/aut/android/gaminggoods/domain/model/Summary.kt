package hu.bme.aut.android.gaminggoods.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    @SerialName("active_giveaways_number")
    val activeGiveawaysNumber: Int,
    @SerialName("worth_estimation_usd")
    val worthEstimationUsd: String
)