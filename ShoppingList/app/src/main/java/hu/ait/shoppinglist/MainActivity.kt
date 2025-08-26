package hu.ait.shoppinglist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.shoppinglist.ui.navigation.ShopScreenRoute
import hu.ait.shoppinglist.ui.navigation.SplashScreenRoute
import hu.ait.shoppinglist.ui.navigation.SummaryScreenRoute
import hu.ait.shoppinglist.ui.screen.ShopListScreen
import hu.ait.shoppinglist.ui.screen.SplashScreen
import hu.ait.shoppinglist.ui.screen.SplashViewModel
import hu.ait.shoppinglist.ui.screen.SummaryScreen
import hu.ait.shoppinglist.ui.theme.AppTypography
import hu.ait.shoppinglist.ui.theme.ShoppingListTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ShoppingListTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute
    ) {
        composable<ShopScreenRoute> {
            ShopListScreen(
                modifier = modifier,
                onInfoClicked = {
                    allShop, homeShop, groceryShop, otherShop, homeTotal, groceryTotal, otherTotal ->
                navController.navigate(SummaryScreenRoute(
                    allShop, homeShop, groceryShop, otherShop, homeTotal, groceryTotal, otherTotal))
            })

        }
        composable<SplashScreenRoute> {
            SplashScreen(onTimeout = {
                navController.navigate(ShopScreenRoute) {
                    popUpTo("shop") { inclusive = true } // Clear the splash screen from back stack
                }
            })
        }
        composable<SummaryScreenRoute> {
            SummaryScreen(onClosed = {navController.navigate(ShopScreenRoute)})
        }
    }

}