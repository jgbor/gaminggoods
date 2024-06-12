package hu.bme.aut.android.gaminggoods.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.gaminggoods.data.settings.Settings
import hu.bme.aut.android.gaminggoods.domain.model.Summary
import hu.bme.aut.android.gaminggoods.feature.notification.AlarmScheduler
import hu.bme.aut.android.gaminggoods.network.deals.DealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeViewState {
    data class Success(
        val notificationOn: Boolean,
        val showAlert: Boolean
    ) : HomeViewState

    object LoadingAlert : HomeViewState
}

sealed interface WorthState {
    object Loading : WorthState
    data class Success(val worth: Summary) : WorthState
}

sealed class HomeEvent {
    data class EnableNotifications(val value: Boolean) : HomeEvent()
    object AlertDismissed : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notificationAlarmScheduler: AlarmScheduler,
    private val settings: Settings,
    private val dealRepository: DealRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState.LoadingAlert as HomeViewState)
    val state = _state.asStateFlow()

    private val _worthState = MutableStateFlow(WorthState.Loading as WorthState)
    val worthState = _worthState.asStateFlow()

    init {
        loadAlert()
        loadWorth()
    }

    private fun loadAlert() {
        _state.update { HomeViewState.LoadingAlert }
        viewModelScope.launch {
            val notificationOn = settings.isNotificationOn.first() ?: true
            val showedAlert = settings.showAlert.first()
            _state.update {
                HomeViewState.Success(
                    notificationOn,
                    showedAlert
                )
            }
        }
    }

    private fun loadWorth() {
        _worthState.update { WorthState.Loading }
        viewModelScope.launch {
            _worthState.update {
                try {
                    val worth = dealRepository.getWorth()
                    WorthState.Success(worth)
                } catch (e: Exception) {
                    WorthState.Loading
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnableNotifications -> {
                viewModelScope.launch {
                    if (settings.isNotificationOn.first() != true && event.value) {
                        notificationAlarmScheduler.setAlarm()
                        settings.setNotificationOn(true)
                    } else if (settings.isNotificationOn.first() == true && !event.value) {
                        notificationAlarmScheduler.cancelAlarm()
                        settings.setNotificationOn(false)
                    }
                }
            }

            is HomeEvent.AlertDismissed -> {
                CoroutineScope(Dispatchers.IO).launch {
                    settings.setShowedAlert(true)
                }
            }
        }
    }
}
