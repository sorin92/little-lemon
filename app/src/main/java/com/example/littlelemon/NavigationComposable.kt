package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navContoller: NavHostController, menuList : List<MenuItemEntity>,androidViewModel : AndroidViewModel){
    val context = LocalContext.current
    val startDestination = determineStartDestination(context)
    NavHost(navController = navContoller, startDestination = startDestination ){
        composable(route=Ondoarding.route){
            Onboarding(navContoller)
        }
        composable(route=Home.route){
            Home(navContoller,menuList,androidViewModel)
        }
        composable(route=Profile.route){
            Profile(navContoller)
        }
    }

}

fun determineStartDestination(context: Context): String {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Retrieve a value from shared preferences indicating whether onboarding is complete
    val onboardingComplete : Boolean = sharedPrefs.getBoolean("onboarding_complete", false)

    // Return "onboarding" if onboarding is not complete, otherwise return "home"
    return if (onboardingComplete) {
        "HomeScreen"
    } else {
        "OnboardingScreen"
    }
}