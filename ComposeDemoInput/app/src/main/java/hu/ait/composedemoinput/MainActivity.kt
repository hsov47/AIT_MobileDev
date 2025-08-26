package hu.ait.composedemoinput

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.composedemoinput.ui.theme.ComposeDemoInputTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDemoInputTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        var myFunc: (Int, String)-> Boolean = {
                number, text ->
            println("Hello $number $text")
            true
        }

        myFunc.invoke(4, "fsdaf")

        demo(1, 3, ::demo2)
        demo(1, 3, onReady = {
            "data"
        })
        demo(1, 3) {
            "data"
        }

        1.aitSmart()
    }
}

fun Int.aitSmart() { //extension function for Int type

}


fun demo(a: Int, b: Int, onReady: ()->String): String { // higher order function
    if (a>0)
        Log.d("tag", onReady())
    return ""
}

fun demo2(): String {
    return "data"
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var textNumA by rememberSaveable { mutableStateOf("") }
    var textNumB by rememberSaveable { mutableStateOf("") }

    var result by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Hello"
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            value = textNumA,
            onValueChange = {
                textNumA = it

            },
            keyboardOptions =
            KeyboardOptions(
                keyboardType =
                KeyboardType.Number
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp), //density independent pixel so same size button no matter density
            value = textNumB,
            onValueChange = {
                textNumB = it // uses the only parameter
            }
        )
        Button(onClick = {
            result = textNumA.toInt() + textNumB.toInt()
        }) {
            Text(text = "Sum")
        }
        Text(
            text = "Result: $result",
            fontSize = 30.sp
        )
    }
}
