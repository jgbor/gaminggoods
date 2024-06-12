package hu.bme.aut.android.gaminggoods.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.android.gaminggoods.domain.model.DealData

@ExperimentalMaterial3Api
@Composable
fun DealItem(
    modifier: Modifier = Modifier,
    deal: DealData,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(deal.thumbnail)
                .build(),
            contentDescription = "Thumbnail",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(150.dp)
                .clip(RoundedCornerShape(10))
                .padding(8.dp)
        )
        Column{
            Text(
                text = deal.title,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = deal.platforms,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}