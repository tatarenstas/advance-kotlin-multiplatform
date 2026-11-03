package ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.onboarding

internal class HasCompletedOnboardingUseCase(
    private val repository: OnboardingRepository,
) {
    suspend operator fun invoke(): Boolean = repository.hasCompletedOnboarding()
}
