package hu.bme.aut.android.gaminggoods.ui.common.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.domain.enums.Platform
import hu.bme.aut.android.gaminggoods.feature.settings.SettingsEvent
import hu.bme.aut.android.gaminggoods.feature.settings.SettingsViewModel
import hu.bme.aut.android.gaminggoods.feature.settings.SettingsViewState

@Composable
fun CheckboxGroup(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state is SettingsViewState.Success) {
        val options = listOf(R.string.playstation4, R.string.playstation5, R.string.xbox_one, R.string.xbox_series, R.string.nintendo_switch, R.string.pc, R.string.android, R.string.ios, R.string.vr)
        val checkedState = mutableMapOf<Int, Boolean>()
        val allChecked = remember { mutableStateOf((state as SettingsViewState.Success).platforms == "") }

        options.forEach { textID ->
            checkedState[textID] = (state as SettingsViewState.Success).platforms.contains(Platform.fromStringId(textID).value)
                    && (state as SettingsViewState.Success).platforms != ""
        }

        val ifChanged : (Int) -> Unit = { textID ->
            checkedState[textID] = !checkedState[textID]!!
            if (!allChecked.value) {
                viewModel.onEvent(
                    SettingsEvent.OnPlatformChanged(
                        textID,
                        checkedState[textID]!!
                    )
                )
            }
        }

        Column(
            modifier = modifier
        ) {
            PlatformMenuItem(
                label = stringResource(id = R.string.all),
                checked = allChecked.value,
                onChange = {
                    allChecked.value = !allChecked.value
                    options.forEach { textID ->
                        ifChanged(textID)
                    }
                    viewModel.onEvent(SettingsEvent.OnPlatformChanged(R.string.all, allChecked.value))
                }
            )

            options.forEach { textID ->
                PlatformMenuItem(
                    label = stringResource(id = textID),
                    checked = checkedState[textID]!! || allChecked.value,
                    onChange = {ifChanged(textID)},
                    enabled = !allChecked.value,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}