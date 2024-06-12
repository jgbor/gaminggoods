package hu.bme.aut.android.gaminggoods.domain.enums

import hu.bme.aut.android.gaminggoods.R

enum class SortBy(val value: String, val stringId: Int) {
    VALUE("value", R.string.value),
    DATE("date", R.string.date),
    POPULARITY("popularity", R.string.popularity),
    NONE("", R.string.empty_str);

    companion object {
        fun fromStringId(stringId: Int): SortBy{
            return values().firstOrNull { it.stringId == stringId } ?: NONE
        }
    }
}