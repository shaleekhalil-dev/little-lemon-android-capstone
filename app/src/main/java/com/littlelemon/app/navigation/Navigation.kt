package com.littlelemon.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.littlelemon.app.screens.Home
import com.littlelemon.app.screens.Onboarding
import com.littlelemon.app.screens.Profile

@Composable
fun Navigation(navController: NavHostController, isLoggedIn: Boolean) {
    val startDestination = if (isLoggedIn) Home.route else Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}