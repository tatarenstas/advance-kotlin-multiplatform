package ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth

import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.RefreshResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.Result

internal interface AuthRepository {
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun refresh(refreshToken: String): Result<RefreshResponse>
}
