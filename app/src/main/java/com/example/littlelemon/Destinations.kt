package com.example.littlelemon

interface Destinations {
    val title : String
    val route : String
}

object Ondoarding : Destinations {
    override val title = "Onboarding"
    override val route = "OnboardingScreen"
}

object Home : Destinations {
    override val title = "Home"
    override val route = "HomeScreen"
}

object Profile : Destinations {
    override val title = "Profile"
    override val route = "ProfileScreen"
}