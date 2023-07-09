package com.leoarmelin.kingscorner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.leoarmelin.kingscorner.components.BigButton
import com.leoarmelin.kingscorner.models.AppRoute
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val playerId by mainViewModel.playerId.collectAsState()

    LaunchedEffect(playerId) {
        if (playerId == null) return@LaunchedEffect
        navController.navigate(AppRoute.Queue.rawValue)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BigButton("Host Game") {
            mainViewModel.createGame()
        }

        Spacer(modifier = Modifier.height(16.dp))

        BigButton("Join Game") {
        }
    }
}