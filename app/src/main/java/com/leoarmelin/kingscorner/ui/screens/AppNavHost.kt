package com.leoarmelin.kingscorner.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leoarmelin.kingscorner.models.AppRoute
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

@Composable
fun AppNavHost(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoute.Splash.rawValue) {
        composable(AppRoute.Splash.rawValue) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(AppRoute.Home.rawValue) {
            HomeScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        composable(AppRoute.Queue.rawValue) {
            QueueScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        composable(AppRoute.Game.rawValue) {
            GameScreen(
                mainViewModel = mainViewModel,
            )
        }
    }
}