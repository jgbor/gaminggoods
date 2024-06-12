package hu.bme.aut.android.gaminggoods

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.gaminggoods.navigation.NavGraph
import hu.bme.aut.android.gaminggoods.ui.theme.GamingGoodsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GamingGoodsTheme {
                NavGraph()
            }
        }
    }
}