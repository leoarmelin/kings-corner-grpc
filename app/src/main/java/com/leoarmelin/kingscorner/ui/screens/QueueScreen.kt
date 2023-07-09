package com.leoarmelin.kingscorner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.leoarmelin.kingscorner.components.BigButton
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

@Composable
fun QueueScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val playerIds by mainViewModel.playerIds.collectAsState()

    // LaunchedEffect to check if game has began && navigate to GameScreen

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(playerIds) { playerId ->
            Text(text = playerId)
        }

        item {
            BigButton(label = "Begin") {
                mainViewModel.beginGame()
            }
        }
    }
}