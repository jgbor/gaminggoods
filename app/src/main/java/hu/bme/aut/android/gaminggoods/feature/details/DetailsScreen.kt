package hu.bme.aut.android.gaminggoods.feature.details

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.ui.common.AutoResizedText
import hu.bme.aut.android.gaminggoods.ui.common.GamingGoodsAppBar
import hu.bme.aut.android.gaminggoods.ui.common.GamingGoodsButton

@ExperimentalMaterial3Api
@Composable
fun DetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            GamingGoodsAppBar(
                onNavigate = onNavigateBack,
                actions = {
                    if (state is DetailsViewState.Success) {
                        IconButton(onClick = {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    (state as DetailsViewState.Success).deal.openGiveawayUrl
                                )
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Share,
                                contentDescription = stringResource(
                                    id = R.string.share
                                )
                            )
                        }
                    }
                },
                title = if (state is DetailsViewState.Success) {
                    (state as DetailsViewState.Success).deal.title
                } else {
                    null
                }
            )
        }
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
                .padding(it)
                .background(brush)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is DetailsViewState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }

                is DetailsViewState.Error -> {
                    AutoResizedText(
                        text = stringResource(id = R.string.details_error),
                        softWrap = true
                    )
                }

                else -> {
                    val successState = state as DetailsViewState.Success
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(successState.deal.image)
                                .build(),
                            contentDescription = "Thumbnail",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width(400.dp)
                                .clip(RoundedCornerShape(10)),
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .weight(5.0f),
                        ) {
                            AutoResizedText(
                                stringResource(
                                    id = R.string.platform_text,
                                    successState.deal.platforms
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                softWrap = true,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            AutoResizedText(
                                if (successState.deal.endDate == "N/A")
                                    stringResource(
                                        id = R.string.date_text,
                                        successState.deal.endDate
                                    )
                                else
                                    stringResource(
                                        id = R.string.date_text,
                                        stringResource(id = R.string.unknown)
                                    ),
                                style = MaterialTheme.typography.bodyLarge,
                            )

                            Spacer(modifier = Modifier.size(32.dp))

                            val scrollState = rememberScrollState()
                            Text(
                                successState.deal.description,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .verticalScroll(scrollState),
                                textAlign = TextAlign.Justify,
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.75f),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            GamingGoodsButton(
                                onClick = {
                                    uriHandler.openUri(successState.deal.openGiveawayUrl)
                                },
                                text = stringResource(id = R.string.open_in_browser),
                            )
                        }
                    }
                }
            }
        }
    }
}