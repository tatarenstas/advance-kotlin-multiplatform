package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.security

import eu.anifantakis.lib.ksafe.KSafe

class AppSecureStorage(
    private val ksafe: KSafe
): SecureStorage {
    override suspend fun getAccessToken(): String {
        return ksafe.get(ACCESS_TOKEN_KEY, "")
    }

    override suspend fun setAccessToken(token: String) {
        ksafe.put(ACCESS_TOKEN_KEY, token)
    }

    override suspend fun getRefreshToken(): String {
        return ksafe.get(REFRESH_TOKEN_KEY, "")
    }

    override suspend fun setRefreshToken(token: String) {
        ksafe.put(REFRESH_TOKEN_KEY, token)
    }
}
