package hu.ait.shoppinglist.ui.screen

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import hu.ait.shoppinglist.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.onPrimary),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(R.drawable.storefront),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize(0.4f),
            tint = colorScheme.primary
        )
    }

    // Delay for a certain time, then navigate to the main screen
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout() // Trigger the navigation to the main screen
    }
}