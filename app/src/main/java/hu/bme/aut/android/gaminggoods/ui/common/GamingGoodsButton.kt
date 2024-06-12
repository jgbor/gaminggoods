package hu.bme.aut.android.gaminggoods.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.ui.theme.GamingGoodsTheme

@Composable
fun GamingGoodsButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
){
    Button(
        onClick = onClick,
        modifier = modifier
            .height(dimensionResource(R.dimen.button_height))
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
    ) {
        AutoResizedText(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun GamingGoodsButtonPreview() {
    GamingGoodsTheme {
        GamingGoodsButton(onClick = {}, text = "Button")
    }
}
