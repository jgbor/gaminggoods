package hu.bme.aut.android.gaminggoods.feature.deals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.gaminggoods.data.settings.Settings
import hu.bme.aut.android.gaminggoods.domain.model.DealData
import hu.bme.aut.android.gaminggoods.network.deals.DealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface DealsViewState{
    data class Success(
        val deals: List<DealData> = emptyList(),
    ): DealsViewState
    object Loading: DealsViewState
    data class Error(val error: Throwable): DealsViewState
}



sealed class DealsEvent {
    object Refresh: DealsEvent()
}

@HiltViewModel
class DealsViewModel @Inject constructor(
    private val dealRepository: DealRepository,
    private val store: Settings
): ViewModel() {
    private val _state = MutableStateFlow(DealsViewState.Loading as DealsViewState)
    val state = _state.asStateFlow()
    init {
        loadDeals()
    }

    private fun loadDeals(){
        _state.update { DealsViewState.Loading }
        viewModelScope.launch {
            _state.update {
                try {
                    val platforms = store.getPlatforms.first()
                    val sortBy = store.getSortBy.first()
                    val type = store.getContentType.first()
                    Log.d("DealsViewModel", "Platforms: $platforms SortBy: $sortBy Type: $type")

                    val deals = dealRepository.getDeals(platforms, sortBy.value, type.value)
                    DealsViewState.Success(deals)
                } catch (e: IOException) {
                    DealsViewState.Error(e)
                } catch (e: HttpException) {
                    DealsViewState.Error(e)
                } catch (e: Exception) {
                    DealsViewState.Success(emptyList())
                }
            }
        }
    }

    fun onEvent(event: DealsEvent){
        when(event){
            DealsEvent.Refresh -> loadDeals()
        }
    }
}
