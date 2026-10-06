package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.security

internal const val ACCESS_TOKEN_KEY = "accessToken"
internal const val REFRESH_TOKEN_KEY = "refreshToken"

internal interface SecureStorage {
    suspend fun getAccessToken(): String
    suspend fun setAccessToken(token: String)
    suspend fun getRefreshToken(): String
    suspend fun setRefreshToken(token: String)
}
