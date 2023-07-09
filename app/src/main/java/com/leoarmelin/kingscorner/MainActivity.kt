package com.leoarmelin.kingscorner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.leoarmelin.kingscorner.ui.screens.GameScreen
import com.leoarmelin.kingscorner.ui.theme.KingsCornerTheme
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KingsCornerTheme {
                GameScreen(
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}