package com.leoarmelin.kingscorner.models

sealed interface AppRoute {
    val rawValue: String
        get() = this.toString()

    object Splash : AppRoute
    object Home : AppRoute
    object Queue : AppRoute
    object Game : AppRoute
}
