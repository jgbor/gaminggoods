package hu.bme.aut.android.gaminggoods.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import hu.bme.aut.android.gaminggoods.domain.enums.ContentType
import hu.bme.aut.android.gaminggoods.domain.enums.SortBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsImpl @Inject constructor(
    private val context: Context
) : Settings{
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private val PLATFORMS = stringPreferencesKey("platforms")
        private val SORT_BY = stringPreferencesKey("sort-by")
        private val TYPE = stringPreferencesKey("type")
        private val NOTIFICATION_ON = booleanPreferencesKey("notification-on")
        private val SHOWED_ALERT = booleanPreferencesKey("show-alert")
    }

    override val getPlatforms: Flow<String> = context.dataStore.data.map {
            preferences ->
        preferences[PLATFORMS] ?: ""
    }

    override suspend fun setPlatforms(platforms: String) {
        context.dataStore.edit { preferences ->
            preferences[PLATFORMS] = platforms
        }
    }

    override val getSortBy: Flow<SortBy> = context.dataStore.data.map {
            preferences ->
        val sortValue = preferences[SORT_BY] ?: SortBy.NONE.name
        SortBy.valueOf(sortValue)
    }

    override val isNotificationOn: Flow<Boolean?> = context.dataStore.data.map {
            preferences ->
        preferences[NOTIFICATION_ON]
    }

    override val showAlert: Flow<Boolean> = context.dataStore.data.map {
            preferences ->
        preferences[SHOWED_ALERT] == null || preferences[SHOWED_ALERT] == false
    }

    override suspend fun setSortBy(sortBy: SortBy) {
        context.dataStore.edit { preferences ->
            preferences[SORT_BY] = sortBy.name
        }
    }

    override val getContentType: Flow<ContentType> = context.dataStore.data.map {
            preferences ->
        val contentValue = preferences[TYPE] ?: ContentType.NONE.name
        ContentType.valueOf(contentValue)
    }

    override suspend fun setContentType(type: ContentType) {
        context.dataStore.edit { preferences ->
            preferences[TYPE] = type.name
        }
    }

    override suspend fun setNotificationOn(notificationOn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_ON] = notificationOn
        }
    }

    override suspend fun setShowedAlert(showedAlert: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOWED_ALERT] = showedAlert
        }
    }
}