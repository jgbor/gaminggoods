package hu.bme.aut.android.gaminggoods.domain.enums

import hu.bme.aut.android.gaminggoods.R

enum class Platform(val value: String, val stringId: Int) {
    PS4("ps4", R.string.playstation4),
    PS5("ps5", R.string.playstation5),
    XBOXONE("xbox-one", R.string.xbox_one),
    XBOXSERIES("xbox-series-xs", R.string.xbox_series),
    SWITCH("switch", R.string.nintendo_switch),
    PC("pc", R.string.pc),
    ANDROID("android", R.string.android),
    IOS("ios", R.string.ios),
    VR("vr", R.string.vr),
    ALL("all", R.string.empty_str);

    companion object {
        fun fromStringId(stringId: Int): Platform{
            return Platform.values().firstOrNull { it.stringId == stringId } ?: ALL
        }
    }
}