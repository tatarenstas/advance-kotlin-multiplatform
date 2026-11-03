package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val startViewModel: StartViewModel = koinViewModel()
    val startRoute by startViewModel.route.collectAsStateWithLifecycle()
    val backStack = rememberNavBackStack(SavedStateConfiguration { }, AppRoute.Loading)

    LaunchedEffect(startRoute) {
        if (startRoute != AppRoute.Loading) {
            backStack.clear()
            backStack.add(startRoute)
        }
    }

    fun navigateTo(route: AppRoute) {
        backStack.clear()
        backStack.add(route)
    }

    DaymarkTheme(darkTheme = false) {
        Surface(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            when (backStack.lastOrNull() as? AppRoute ?: AppRoute.Loading) {
                AppRoute.Loading -> AppLoadingScreen()
                AppRoute.Onboarding -> OnboardingScreen(
                    onFinish = { navigateTo(AppRoute.SignIn) },
                )
                AppRoute.SignIn -> AuthScreen()
            }
        }
    }
}

@Composable
private fun AppLoadingScreen() {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}
