package hu.ait.composehelloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hu.ait.composehelloworld.ui.theme.ComposeHelloWorldTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // expects composable function, here it's lambda
        setContent {
            ComposeHelloWorldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoTime()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var textContent by rememberSaveable {mutableStateOf("")}

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Hello $name! $textContent",
            )

        Text(
            text = "Date: $textContent",
            fontSize = 30.sp
        )
        Button (
            onClick = {
                textContent = Date(
                    System.currentTimeMillis()).toString()
                }
            ) {
            Text(text = "Click me")
            }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeHelloWorldTheme {
        Greeting("AIT")
    }
}

@Composable
fun RowDemo() {
    Row(horizontalArrangement = Arrangement.Center)
    {

    }
}

@Composable
fun DemoTime() {


    Column (){
        var timeText by mutableStateOf("")
        Text(text = timeText)
        Button(
            onClick = {
                timeText = Date(System.currentTimeMillis()).toString()
            }
        ) {
            Text("Show time")
        }
    }
}



















