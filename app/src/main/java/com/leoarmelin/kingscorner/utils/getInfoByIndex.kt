package com.leoarmelin.kingscorner.utils

import androidx.compose.ui.Alignment

fun getAlignmentByIndex(index: Int) = when (index) {
    4 -> Alignment.TopStart
    0 -> Alignment.TopCenter
    5 -> Alignment.TopEnd
    1 -> Alignment.CenterStart
    2 -> Alignment.CenterEnd
    6 -> Alignment.BottomStart
    3 -> Alignment.BottomCenter
    7 -> Alignment.BottomEnd
    else -> Alignment.Center
}

fun getRotationByIndex(index: Int) = when (index) {
    4 -> -30f
    5 -> 30f
    6 -> 30f
    7 -> -30f
    else -> 0f
}