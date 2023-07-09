package com.leoarmelin.kingscorner.extensions

import androidx.compose.ui.graphics.Color
import com.leoarmelin.kingscorner.models.CardSuit

fun CardSuit.getColor() = when (this) {
    CardSuit.Spade, CardSuit.Club -> Color.Black
    CardSuit.Diamond, CardSuit.Heart -> Color.Red
    else -> Color.Transparent
}

fun CardSuit.getIcon() = when (this) {
    CardSuit.Heart -> "♥"
    CardSuit.Club -> "♠"
    CardSuit.Diamond -> "♦"
    CardSuit.Spade -> "♣"
    else -> ""
}