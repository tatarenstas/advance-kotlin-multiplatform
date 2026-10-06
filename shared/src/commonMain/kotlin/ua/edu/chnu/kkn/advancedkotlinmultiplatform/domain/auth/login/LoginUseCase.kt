package ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.login

import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.Result
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.AuthRepository

internal class LoginUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(username: String, password: String): Result<Unit> {
        return authRepository.login(username, password)
    }
}
