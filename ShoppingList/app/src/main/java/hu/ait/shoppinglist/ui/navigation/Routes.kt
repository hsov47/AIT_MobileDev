package hu.ait.shoppinglist.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object ShopScreenRoute

@Serializable
object SplashScreenRoute

@Serializable
data class SummaryScreenRoute(
    val allShop: Int,
    val homeShop: Int,
    val groceryShop: Int,
    val otherShop: Int,
    val homeTotal: Int,
    val groceryTotal: Int,
    val otherTotal: Int
)