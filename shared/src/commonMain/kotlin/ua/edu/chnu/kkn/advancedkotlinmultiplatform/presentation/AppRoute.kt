package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.navigation3.runtime.NavKey

internal sealed class AppRoute : NavKey {
    data object Loading : AppRoute()
    data object Onboarding : AppRoute()
    data object SignIn : AppRoute()
    data object Home : AppRoute()
}
