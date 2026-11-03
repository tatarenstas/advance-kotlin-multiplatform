package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.session.IsAuthenticatedUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.onboarding.HasCompletedOnboardingUseCase

internal class StartViewModel(
    private val hasCompletedOnboarding: HasCompletedOnboardingUseCase,
    private val isAuthenticated: IsAuthenticatedUseCase,
) : ViewModel() {
    private val _route = MutableStateFlow<AppRoute>(AppRoute.Loading)
    val route: StateFlow<AppRoute> = _route.asStateFlow()

    init {
        refreshStartRoute()
    }

    fun refreshStartRoute() {
        viewModelScope.launch {
            _route.value = when {
                !hasCompletedOnboarding() -> AppRoute.Onboarding
                isAuthenticated() -> AppRoute.Home
                else -> AppRoute.SignIn
            }
        }
    }
}
