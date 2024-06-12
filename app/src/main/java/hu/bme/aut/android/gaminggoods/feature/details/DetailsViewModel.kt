package hu.bme.aut.android.gaminggoods.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.gaminggoods.domain.model.DealData
import hu.bme.aut.android.gaminggoods.network.deals.DealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface DetailsViewState{
    data class Success(
        val deal: DealData
    ): DetailsViewState
    object Loading: DetailsViewState
    data class Error(val error: Throwable): DetailsViewState

}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val dealRepository: DealRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailsViewState.Loading as DetailsViewState)
    val state = _state.asStateFlow()

    init {
        load()
    }

    private fun load() {
        val dealId = checkNotNull<Int>(savedState["id"])
        _state.update { DetailsViewState.Loading }
        viewModelScope.launch {
            _state.update {
                try {
                    val result = dealRepository.getDeal(dealId)
                    DetailsViewState.Success(result)
                } catch (e: IOException) {
                    DetailsViewState.Error(e)
                } catch (e: HttpException) {
                    DetailsViewState.Error(e)
                } catch (e: Exception) {
                    DetailsViewState.Error(e)
                }
            }
        }
    }
}