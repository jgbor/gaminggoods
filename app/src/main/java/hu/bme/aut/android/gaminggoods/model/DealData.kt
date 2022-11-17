package hu.bme.aut.android.gaminggoods.model

import java.io.Serializable

data class DealData(
    var id: Int,
    var title: String,
    var worth: String,
    var thumbnail: String,
    var image: String,
    var description: String,
    var instructions: String,
    var open_giveaway_url: String,
    var type: String,
    var platforms: String,
    var end_date: String,
    var users: Int,
    var status: String,
    var gamerpower_url: String
): Serializable