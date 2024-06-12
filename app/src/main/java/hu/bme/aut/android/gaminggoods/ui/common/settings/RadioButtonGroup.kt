package hu.bme.aut.android.gaminggoods.ui.common.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.gaminggoods.R
import hu.bme.aut.android.gaminggoods.ui.common.AutoResizedText
import hu.bme.aut.android.gaminggoods.ui.theme.GamingGoodsTheme

@Composable
fun RadioButtonGroup(
    radioOptions : List<Int>,
    state: String,
    modifier: Modifier = Modifier,
    onChange: (Int) -> Unit = {},
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(state) }
    Column(
        modifier = modifier
    ) {
        radioOptions.forEach { textID ->
            val text = stringResource(id =textID)
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            if (text == selectedOption) {
                                onOptionSelected("")
                                onChange(R.string.empty_str)
                            } else {
                                onOptionSelected(text)
                                onChange(textID)
                            }
                        },
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        if (text == selectedOption) {
                            onOptionSelected("")
                            onChange(R.string.empty_str)
                        } else {
                            onOptionSelected(text)
                            onChange(textID)
                        }
                    },
                )
                AutoResizedText(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun RadioButtonGroupPreview() {
    GamingGoodsTheme {
        RadioButtonGroup(
            radioOptions = listOf(R.string.game, R.string.loot),
            state = stringResource(id = R.string.game)
        )
    }
}