package hu.bme.aut.android.gaminggoods.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalMaterial3Api
@Composable
fun GamingGoodsAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onNavigate: () -> Unit,
    navigationIcon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
        },
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    itemColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    TopAppBar(
        modifier = modifier,
        title = { title?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) } },
        navigationIcon = {
            IconButton(onClick = onNavigate) {
                navigationIcon()
            }},
        actions = actions,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = itemColor,
            actionIconContentColor = itemColor,
            navigationIconContentColor = itemColor
        )
    )
}