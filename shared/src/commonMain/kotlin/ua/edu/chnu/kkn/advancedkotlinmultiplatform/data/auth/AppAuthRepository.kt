package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth

import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.LoginResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.RefreshResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service.AuthApiService
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.Result
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.map
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.security.SecureStorage
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.AuthRepository

internal class AppAuthRepository(
    private val secureStorage: SecureStorage,
    private val authApiService: AuthApiService,
) : AuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit> {
        return authApiService.login(username, password).map {
            secureStorage.setAccessToken(it.accessToken)
            secureStorage.setRefreshToken(it.refreshToken)
        }
    }

    override suspend fun refresh(refreshToken: String): Result<RefreshResponse> {
        return authApiService.refresh(refreshToken)
    }
}
