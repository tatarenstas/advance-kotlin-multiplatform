package ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.onboarding

internal class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository,
) {
    suspend operator fun invoke() = repository.completeOnboarding()
}
