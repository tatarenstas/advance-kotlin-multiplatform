package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.onboarding

import com.russhwolf.settings.Settings
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.onboarding.OnboardingRepository

internal class SettingsOnboardingRepository(
    private val settings: Settings,
) : OnboardingRepository {
    override suspend fun hasCompletedOnboarding(): Boolean = settings.getBoolean(KEY, false)

    override suspend fun completeOnboarding() {
        settings.putBoolean(KEY, true)
    }

    private companion object {
        const val KEY = "has_seen_onboarding"
    }
}
