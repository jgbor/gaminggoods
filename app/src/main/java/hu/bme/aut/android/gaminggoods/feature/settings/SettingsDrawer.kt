package hu.bme.aut.android.gaminggoods.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.ui.common.AutoResizedText
import hu.bme.aut.android.gaminggoods.ui.common.settings.CheckboxGroup
import hu.bme.aut.android.gaminggoods.ui.common.settings.RadioButtonGroup
import hu.bme.aut.android.gaminggoods.ui.model.toUiText
import hu.bme.aut.android.gaminggoods.ui.theme.GamingGoodsTheme
import hu.bme.aut.android.gaminggoods.ui.utl.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun GamingGoodsDrawer(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: SettingsViewModel = hiltViewModel(),
    onClose: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> { onClose() }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f),
            ) {
                LaunchedEffect(drawerState) {
                    snapshotFlow { drawerState.isOpen }
                        .distinctUntilChanged()
                        .filter { !it }
                        .collect {
                            viewModel.onEvent(SettingsEvent.SaveSettings)
                        }
                }

                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    when (state) {
                        is SettingsViewState.Error -> {
                            Text(
                                text = (state as SettingsViewState.Error).error.toUiText()
                                    .asString(LocalContext.current),
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        is SettingsViewState.Loading -> {
                            CircularProgressIndicator()
                        }

                        else -> {
                            val sortBy = (state as SettingsViewState.Success).sortBy
                            val type = (state as SettingsViewState.Success).type

                            val scrollState = rememberScrollState()
                            Column(
                                modifier = Modifier.verticalScroll(scrollState)
                            ) {
                                AutoResizedText(
                                    text = stringResource(id = R.string.platforms),
                                    modifier = Modifier.padding(8.dp)
                                    )
                                CheckboxGroup(viewModel = viewModel)
                                Spacer(Modifier.height(20.dp))

                                AutoResizedText(
                                    text = stringResource(id = R.string.sort_by),
                                    modifier = Modifier.padding(8.dp)
                                )
                                RadioButtonGroup(
                                    radioOptions = listOf(
                                        R.string.value,
                                        R.string.date,
                                        R.string.popularity,
                                    ),
                                    stringResource(id = sortBy.stringId),
                                    onChange = { viewModel.onEvent(SettingsEvent.OnSortByChanged(it)) }
                                )
                                Spacer(Modifier.height(20.dp))

                                AutoResizedText(
                                    text = stringResource(id = R.string.content_type),
                                    modifier = Modifier.padding(8.dp)
                                )
                                RadioButtonGroup(
                                    radioOptions = listOf(
                                        R.string.game,
                                        R.string.loot,
                                    ),
                                    stringResource(id = type.stringId),
                                    onChange = { viewModel.onEvent(SettingsEvent.OnContentTypeChanged(it)) }
                                )
                            }
                        }
                    }
                }
            }
        },
        content = content
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun GamingGoodsDrawerPreview() {
    GamingGoodsTheme {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        GamingGoodsDrawer(
            drawerState = drawerState,
            scope = scope
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                Spacer(Modifier.height(20.dp))
                Button(onClick = { scope.launch { drawerState.open() } }) {
                    Text("Click to open")
                }
            }
        }
    }
}