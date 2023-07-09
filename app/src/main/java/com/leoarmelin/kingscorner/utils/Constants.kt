package com.leoarmelin.kingscorner.utils

import androidx.compose.ui.geometry.Offset

object Constants {
    const val cardHeight = 100
    const val cardRatio = 0.7f
    const val cardWidth = cardHeight * cardRatio
    val cardMiddleOffset = Offset(cardWidth / 2, cardHeight / 2f)
}