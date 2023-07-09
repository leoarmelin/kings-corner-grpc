package com.leoarmelin.kingscorner.extensions

import com.leoarmelin.kingscorner.models.CardRank

fun CardRank.getChar() = when (this) {
    CardRank.Ace -> "A"
    CardRank.Two -> "2"
    CardRank.Three -> "3"
    CardRank.Four -> "4"
    CardRank.Five -> "5"
    CardRank.Six -> "6"
    CardRank.Seven -> "7"
    CardRank.Eight -> "8"
    CardRank.Nine -> "9"
    CardRank.Ten -> "10"
    CardRank.Jack -> "J"
    CardRank.Queen -> "Q"
    CardRank.King -> "K"
    else -> ""
}