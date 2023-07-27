package com.leoarmelin.kingscorner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.leoarmelin.kingscorner.models.AppRoute
import com.leoarmelin.kingscorner.ui.theme.DarkBlue500
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(AppRoute.Home.rawValue)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue500),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "King's\nCorner",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 44.sp
        )
    }
}