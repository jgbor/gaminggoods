package hu.bme.aut.android.gaminggoods.domain.enums

import hu.bme.aut.android.gaminggoods.R

enum class ContentType(val value: String, val stringId: Int) {
    GAME("game", R.string.game),
    LOOT("loot", R.string.loot),
    NONE("", R.string.empty_str);

    companion object {
        fun fromStringId(stringId: Int): ContentType {
            return values().firstOrNull { it.stringId == stringId } ?: NONE
        }
    }
}