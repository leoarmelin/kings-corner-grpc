package com.leoarmelin.kingscorner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.leoarmelin.kingscorner.ui.theme.KingsCornerTheme
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KingsCornerTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Button(onClick = { mainViewModel.createGame() }) {
                        Text("Create")
                    }

                    Button(onClick = { mainViewModel.beginGame() }) {
                        Text("Begin")
                    }
                }
            }
        }
    }
}