package hu.bme.aut.android.gaminggoods.feature.deals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MenuOpen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.feature.settings.GamingGoodsDrawer
import hu.bme.aut.android.gaminggoods.ui.common.AutoResizedText
import hu.bme.aut.android.gaminggoods.ui.common.DealItem
import hu.bme.aut.android.gaminggoods.ui.common.GamingGoodsAppBar
import kotlinx.coroutines.launch
import retrofit2.HttpException

@ExperimentalMaterial3Api
@Composable
fun DealsScreen(
    onListItemClick: (Int) -> Unit,
    viewModel: DealsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            GamingGoodsAppBar(
                onNavigate = {
                    if (drawerState.isClosed) {
                        scope.launch { drawerState.open() }
                    } else {
                        scope.launch { drawerState.close() }
                    }
                },
                navigationIcon = {
                    if (drawerState.isClosed) {
                        Icon(imageVector = Icons.Rounded.Menu, null)
                    } else {
                        Icon(imageVector = Icons.Rounded.MenuOpen, null)
                    }
                },
                title = stringResource(id = R.string.deals)
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            GamingGoodsDrawer(
                drawerState = drawerState,
                scope = scope,
                onClose = { viewModel.onEvent(DealsEvent.Refresh) }
            ) {
                val brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush),
                    contentAlignment = Alignment.Center
                ) {
                    when (state) {
                        is DealsViewState.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                        }

                        is DealsViewState.Error -> {
                            AutoResizedText(
                                text = if ((state as DealsViewState.Error).error is HttpException)
                                    stringResource(id = R.string.network_error)
                                else
                                    stringResource(id = R.string.no_internet)
                            )
                        }

                        else -> {
                            val success = state as DealsViewState.Success
                            if (success.deals.isEmpty()) {
                                AutoResizedText(text = stringResource(id = R.string.nothing_found))
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(5.dp))
                                ) {
                                    items(success.deals.size) { i ->
                                        DealItem(
                                            onClick = { onListItemClick(success.deals[i].id) },
                                            deal = success.deals[i]
                                        )
                                        if (i != success.deals.lastIndex) {
                                            Divider(
                                                thickness = 2.dp,
                                                color = Color.Transparent
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}