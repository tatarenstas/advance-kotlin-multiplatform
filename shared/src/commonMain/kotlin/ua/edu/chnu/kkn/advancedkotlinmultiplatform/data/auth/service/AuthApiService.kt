package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service

import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.LoginResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.RefreshResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.Result

internal const val AUTH = "auth"
internal const val LOGIN = "/login"
internal const val REFRESH = "/refresh"

internal interface AuthApiService {
    suspend fun login(username: String, password: String): Result<LoginResponse>
    suspend fun refresh(refreshToken: String): Result<RefreshResponse>
}
