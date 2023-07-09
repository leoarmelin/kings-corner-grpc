package com.leoarmelin.kingscorner.models

enum class CardRank(val value: Int) {
    Blank(-1), // Else case
    Ace(1),
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Jack(11),
    Queen(12),
    King(13),
}