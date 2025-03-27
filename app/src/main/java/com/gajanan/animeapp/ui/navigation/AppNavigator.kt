package com.gajanan.animeapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gajanan.animeapp.ui.screens.DetailScreen
import com.gajanan.animeapp.ui.screens.HomeScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController, startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen { animeId ->
                navController.navigate(Screen.DetailScreen.withArgs(animeId.toString()))
            }
        }
        composable(
            route = Screen.DetailScreen.route + "/{animeId}",
            arguments = listOf(
                navArgument("animeId"){
                    type = NavType.IntType
                }
            )
            ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId")
            DetailScreen(animeId = animeId ?: -1)
        }
    }
}