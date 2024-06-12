package hu.bme.aut.android.gaminggoods.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DealData(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("worth")
    val worth: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("image")
    val image: String,
    @SerialName("description")
    val description: String,
    @SerialName("instructions")
    val instructions: String,
    @SerialName("open_giveaway_url")
    val openGiveawayUrl: String,
    @SerialName("type")
    val type: String,
    @SerialName("platforms")
    val platforms: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("users")
    val users: Int,
    @SerialName("status")
    val status: String,
    @SerialName("gamerpower_url")
    val gamerpowerUrl: String,
    @SerialName("published_date")
    val publishedDate: String? = null,
    @SerialName("open_giveaway")
    val openGiveaway: String? = null,
)