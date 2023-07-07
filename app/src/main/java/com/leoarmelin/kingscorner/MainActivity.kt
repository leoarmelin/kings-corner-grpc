package com.leoarmelin.kingscorner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.leoarmelin.kingscorner.ui.theme.KingsCornerTheme
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val channel = ManagedChannelBuilder
        .forAddress("10.0.2.2", 50051)
        .usePlaintext()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KingsCornerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            createGame()
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun createGame() {
        val request = CreateRequest.newBuilder().build()
        val stub = GameServiceGrpc.newBlockingStub(channel)

        lifecycleScope.launch(Dispatchers.IO) {
            stub.create(request).asFlow().collect { joinResponse ->
                Log.e("proto", joinResponse.playerId)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KingsCornerTheme {
        Greeting("Android")
    }
}