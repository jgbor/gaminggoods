package hu.bme.aut.android.gaminggoods.feature.home

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.ui.common.AutoResizedText
import hu.bme.aut.android.gaminggoods.ui.common.GamingGoodsButton
import hu.bme.aut.android.gaminggoods.ui.theme.GamingGoodsTheme

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onDealsNavigate: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    val worthState = viewModel.worthState.collectAsStateWithLifecycle()

    val contactsPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            permissions = listOf(
                android.Manifest.permission.POST_NOTIFICATIONS
            )
        )
    } else {
        rememberMultiplePermissionsState(permissions = listOf())
    }

    Scaffold {
        val brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(brush)
                .padding(16.dp)
        ) {
            if (state.value is HomeViewState.Success) {
                val showAlert =
                    remember { mutableStateOf((state.value as HomeViewState.Success).showAlert) }
                if (contactsPermissions.allPermissionsGranted) {
                    LaunchedEffect(key1 = contactsPermissions.allPermissionsGranted) {
                        viewModel.onEvent(HomeEvent.EnableNotifications(true))
                    }
                } else {
                    if (showAlert.value) {
                        AlertDialog(
                            onDismissRequest = { showAlert.value = false },
                            confirmButton = {
                                GamingGoodsButton(
                                    text = stringResource(id = R.string.button_confirm),
                                    onClick = {
                                        contactsPermissions.launchMultiplePermissionRequest()
                                        showAlert.value = false
                                    }
                                )
                            },
                            dismissButton = {
                                GamingGoodsButton(
                                    text = stringResource(id = R.string.button_decline),
                                    onClick = {
                                        viewModel.onEvent(HomeEvent.AlertDismissed)
                                        showAlert.value = false
                                    },
                                )
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.notification_permission_dialog_text),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                        )
                    }
                }
                val checked =
                    remember { mutableStateOf((state.value as HomeViewState.Success).notificationOn) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (checked.value) Icons.Rounded.Notifications else Icons.Rounded.NotificationsOff,
                        contentDescription = stringResource(
                            R.string.notifications_switch
                        ),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Switch(
                        checked = checked.value,
                        onCheckedChange = { value ->
                            checked.value = value
                            viewModel.onEvent(HomeEvent.EnableNotifications(value))
                        },
                        enabled = contactsPermissions.allPermissionsGranted
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.size(48.dp))
                AutoResizedText(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    softWrap = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Image(
                    painter = painterResource(id = R.drawable.round_videogame_asset_200),
                    contentDescription = null,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom,
                ) {
                if (worthState.value is WorthState.Success) {
                    AutoResizedText(
                        text = stringResource(
                            id = R.string.worth,
                            (worthState.value as WorthState.Success).worth.activeGiveawaysNumber,
                            (worthState.value as WorthState.Success).worth.worthEstimationUsd
                        ),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
                GamingGoodsButton(
                    onClick = onDealsNavigate,
                    text = stringResource(id = R.string.button_deals)
                )
            }
        }
    }
}

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun HomePreview() {
    GamingGoodsTheme {
        HomeScreen {}
    }
}