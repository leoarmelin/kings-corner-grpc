package com.leoarmelin.kingscorner.extensions

import com.leoarmelin.kingscorner.Card
import com.leoarmelin.kingscorner.models.CardRank
import com.leoarmelin.kingscorner.models.CardSuit

val Card.appRank: CardRank
    get() = when (this.rank) {
        CardRank.Ace.value -> CardRank.Ace
        CardRank.Two.value -> CardRank.Two
        CardRank.Three.value -> CardRank.Three
        CardRank.Four.value -> CardRank.Four
        CardRank.Five.value -> CardRank.Five
        CardRank.Six.value -> CardRank.Six
        CardRank.Seven.value -> CardRank.Seven
        CardRank.Eight.value -> CardRank.Eight
        CardRank.Nine.value -> CardRank.Nine
        CardRank.Ten.value -> CardRank.Ten
        CardRank.Jack.value -> CardRank.Jack
        CardRank.Queen.value -> CardRank.Queen
        CardRank.King.value -> CardRank.King
        else -> CardRank.Blank
    }

val Card.appSuit: CardSuit
    get() = when (this.suit) {
        CardSuit.Spade.value -> CardSuit.Spade
        CardSuit.Diamond.value -> CardSuit.Diamond
        CardSuit.Club.value -> CardSuit.Club
        CardSuit.Heart.value -> CardSuit.Heart
        else -> CardSuit.Blank
    }