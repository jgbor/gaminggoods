package hu.bme.aut.android.gaminggoods.ui.common.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.gaminggoods.ui.common.AutoResizedText
import hu.bme.aut.android.gaminggoods.ui.theme.GamingGoodsTheme

@Composable
fun PlatformMenuItem(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onChange: () -> Unit = {},
    enabled: Boolean = true
) {
    Row(
        modifier.clickable(
            onClick = {
                onChange()
            },
            enabled = enabled
        )
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onChange()
            },
            enabled = enabled
        )
        AutoResizedText(
            text = label,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
@Preview
fun PlatformDrawerItemPreview() {
    GamingGoodsTheme {
        val selected = remember { mutableStateOf(true) }
        PlatformMenuItem(
            label = "Option 1",
            checked = selected.value,
            onChange = { selected.value = !selected.value }
        )
    }
}