package com.ayubu.qreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayubu.qreader.screens.ReaderSplashScreen
import com.ayubu.qreader.screens.details.BookDetailsScreen
import com.ayubu.qreader.screens.home.Home
import com.ayubu.qreader.screens.login.ReaderLoginScreen
import com.ayubu.qreader.screens.search.SearchScreen
import com.ayubu.qreader.screens.stats.ReaderStatsScreen
import com.ayubu.qreader.screens.update.BookUpdateScreen

//step 5 Composable screen
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination =ReaderScreens.SplashScreen.name ){

        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            Home(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
           ReaderStatsScreen(navController = navController)
        }
        composable(ReaderScreens.UpdateScreen.name){
            BookUpdateScreen(navController = navController)
        }
        composable(ReaderScreens.DetailScreen.name){
            BookDetailsScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }
    }
}