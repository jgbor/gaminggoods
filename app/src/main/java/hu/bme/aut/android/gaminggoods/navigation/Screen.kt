package hu.bme.aut.android.gaminggoods.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Deals: Screen("deals")
    object DealDetails: Screen("dealDetails/{id}"){
        fun passId(id: Int) = "dealDetails/$id"
    }
}
