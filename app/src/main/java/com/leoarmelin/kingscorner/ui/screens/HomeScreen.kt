package com.leoarmelin.kingscorner.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.leoarmelin.kingscorner.R
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

    BackgroundMedia()

    Column(
        modifier = Modifier
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

@Composable
fun BackgroundMedia() {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val musics = remember { listOf(R.raw.song_one, R.raw.song_two, R.raw.song_three) }
    var musicToPlay by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }
    val mediaPlayer = remember(musicToPlay, isPlaying) {
        if (!isPlaying) return@remember null
        val music = musics.getOrElse(musicToPlay) {
            return@remember MediaPlayer.create(context, musics.first())
        }
        MediaPlayer.create(context, music)
    }

    LaunchedEffect(mediaPlayer) {
        if (!isPlaying) return@LaunchedEffect
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.stop()
            it.release()
            if (musicToPlay == 2) {
                musicToPlay = 0
            } else {
                musicToPlay++
            }
        }
    }

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    isPlaying = false
                }
                Lifecycle.Event.ON_RESUME -> {
                    isPlaying = true
                }
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}