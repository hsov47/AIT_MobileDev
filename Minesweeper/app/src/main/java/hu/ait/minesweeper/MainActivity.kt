package hu.ait.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import hu.ait.minesweeper.ui.theme.MinesweeperTheme
import hu.ait.minesweeper.ui.screen.MinesweeperScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinesweeperTheme {
                Surface(tonalElevation = 5.dp) {
                    Scaffold(modifier = Modifier.fillMaxSize())
                    { innerPadding ->
                        MinesweeperScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column (modifier = modifier){
        val flipController = rememberFlipController()
        Flippable(
            frontSide = {
            },
            backSide = {},
            flipController,
            flipOnTouch = false

        )

        Button(onClick = {
            flipController.flip()
        }) { }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MinesweeperTheme {
        Greeting("Android")
    }
}