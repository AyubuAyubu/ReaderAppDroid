package com.ayubu.qreader.screens.search

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ayubu.qreader.components.ReaderAppBar
import com.ayubu.qreader.navigation.ReaderScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController){
    Scaffold(topBar = {
        ReaderAppBar(
            title ="Search Books",
            icon= Icons.Default.ArrowBack,
            navController =navController,
            showProfile=false
            ){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) {

    }
}