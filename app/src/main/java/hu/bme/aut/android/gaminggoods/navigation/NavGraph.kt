package hu.bme.aut.android.gaminggoods.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.bme.aut.android.gaminggoods.feature.deals.DealsScreen
import hu.bme.aut.android.gaminggoods.feature.details.DetailsScreen
import hu.bme.aut.android.gaminggoods.feature.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route){
            HomeScreen(
                onDealsNavigate = {
                    navController.navigate(Screen.Deals.route)
                }
            )
        }
        composable(Screen.Deals.route){
            DealsScreen(
                onListItemClick = {
                    navController.navigate(Screen.DealDetails.passId(it))
                }
            )
        }
        composable(
            route = Screen.DealDetails.route,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                }
            )
        ){
            DetailsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}