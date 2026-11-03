package ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.session

import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.AuthRepository

internal class IsAuthenticatedUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean = authRepository.isAuthenticated()
}
