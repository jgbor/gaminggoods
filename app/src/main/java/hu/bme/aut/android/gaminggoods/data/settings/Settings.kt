package hu.bme.aut.android.gaminggoods.data.settings

import hu.bme.aut.android.gaminggoods.domain.enums.ContentType
import hu.bme.aut.android.gaminggoods.domain.enums.SortBy
import kotlinx.coroutines.flow.Flow

interface Settings{
    val getPlatforms: Flow<String>
    val getSortBy: Flow<SortBy>
    val getContentType: Flow<ContentType>
    val isNotificationOn: Flow<Boolean?>
    val showAlert: Flow<Boolean>
    suspend fun setSortBy(sortBy: SortBy)
    suspend fun setContentType(type: ContentType)
    suspend fun setPlatforms(platforms: String)
    suspend fun setNotificationOn(notificationOn: Boolean)
    suspend fun  setShowedAlert(showedAlert: Boolean)
}