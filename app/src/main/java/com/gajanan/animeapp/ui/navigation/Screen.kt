package com.gajanan.animeapp.ui.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_page")
    data object DetailScreen : Screen("detail_page")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}