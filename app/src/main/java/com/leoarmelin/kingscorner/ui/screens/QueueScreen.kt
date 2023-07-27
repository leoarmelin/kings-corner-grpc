package com.leoarmelin.kingscorner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.leoarmelin.kingscorner.components.BigButton
import com.leoarmelin.kingscorner.models.AppRoute
import com.leoarmelin.kingscorner.ui.theme.Cream500
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

@Composable
fun QueueScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val playerList by mainViewModel.playerIds.collectAsState()
    val isStarted by mainViewModel.isStarted.collectAsState()

    LaunchedEffect(isStarted) {
        if (!isStarted) return@LaunchedEffect

        navController.navigate(AppRoute.Game.rawValue)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream500),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(playerList) { player ->
            Text(text = player.id)
        }

        item {
            BigButton(label = "Begin") {
                mainViewModel.beginGame()
            }
        }
    }
}