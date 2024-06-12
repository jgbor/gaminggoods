package hu.bme.aut.android.gaminggoods.feature.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.gaminggoods.data.settings.Settings
import hu.bme.aut.android.gaminggoods.domain.enums.ContentType
import hu.bme.aut.android.gaminggoods.domain.enums.Platform
import hu.bme.aut.android.gaminggoods.domain.enums.SortBy
import hu.bme.aut.android.gaminggoods.ui.model.toUiText
import hu.bme.aut.android.gaminggoods.ui.utl.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SettingsViewState{
    data class Success(
        val platforms: String,
        val sortBy: SortBy,
        val type: ContentType
    ): SettingsViewState
    object Loading: SettingsViewState
    data class Error(val error: Throwable): SettingsViewState
}

sealed class SettingsEvent {
    object SaveSettings: SettingsEvent()
    data class OnPlatformChanged(val id: Int, val value: Boolean): SettingsEvent()
    data class OnSortByChanged(val id: Int): SettingsEvent()
    data class OnContentTypeChanged(val id: Int): SettingsEvent()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val store: Settings
):ViewModel() {
    private val _state = MutableStateFlow(SettingsViewState.Loading as SettingsViewState)
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadSettings()
    }

    private fun loadSettings(){
        _state.update { SettingsViewState.Loading }
        viewModelScope.launch {
            _state.update{
                try {
                    val platforms = store.getPlatforms.first()
                    val sortBy = store.getSortBy.first()
                    val type = store.getContentType.first()
                    Log.d("SettingsViewModel", "Platforms: $platforms SortBy: $sortBy Type: $type")
                    SettingsViewState.Success(platforms, sortBy, type)
                } catch (e: Exception) {
                    SettingsViewState.Error(e)
                }
            }
        }
    }

    private fun saveSettings(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                store.setPlatforms((state.value as SettingsViewState.Success).platforms)
                store.setSortBy((state.value as SettingsViewState.Success).sortBy)
                store.setContentType((state.value as SettingsViewState.Success).type)
                _uiEvent.send(UiEvent.Success)
            }catch (e: Exception){
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun updateSortBy(id: Int){
        _state.update {
            when(val state = it){
                is SettingsViewState.Success -> state.copy(sortBy = SortBy.fromStringId(id))
                else -> state
            }
        }
    }

    private fun updateType(id: Int){
        _state.update {
            when(val state = it){
                is SettingsViewState.Success -> state.copy(type = ContentType.fromStringId(id))
                else -> state
            }
        }
    }

    private fun updatePlatform(id: Int, value: Boolean){
        _state.update {
            when(val state = it){
                is SettingsViewState.Success -> {
                    val changedPlatform = Platform.fromStringId(id).value
                    val newPlatforms = createPlatformsString(state, changedPlatform, value)
                    state.copy(platforms = newPlatforms)
                }
                else -> state
            }
        }
    }

    private fun createPlatformsString(currentState : SettingsViewState.Success,changedPlatform: String, value: Boolean): String{
        lateinit var res : String
        if (changedPlatform == "all" && value) {
            res = ""
        }else if (changedPlatform == "all"){
            res = ""
        } else {
            res = currentState.platforms
            if (value) {
                if (res != "") {
                    res += "."
                }
                res += changedPlatform
            } else {
                res = res.replace(changedPlatform, "")
                if (res != "") {
                    if (res[res.length - 1] == '.') {
                        res = res.substring(0, res.length - 1)
                    } else if (res[0] == '.') {
                        res = res.substring(1)
                    }
                    res = res.replace("..", ".")
                }
            }
        }
        return res
    }

    fun onEvent(event: SettingsEvent){
        when(event){
            is SettingsEvent.SaveSettings -> saveSettings()
            is SettingsEvent.OnPlatformChanged -> updatePlatform(event.id, event.value)
            is SettingsEvent.OnSortByChanged -> updateSortBy(event.id)
            is SettingsEvent.OnContentTypeChanged -> updateType(event.id)
        }
    }
}
