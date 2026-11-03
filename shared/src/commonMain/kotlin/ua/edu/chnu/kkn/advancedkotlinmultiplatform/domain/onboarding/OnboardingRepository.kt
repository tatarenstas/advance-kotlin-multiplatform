package ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.onboarding

internal interface OnboardingRepository {
    suspend fun hasCompletedOnboarding(): Boolean
    suspend fun completeOnboarding()
}
