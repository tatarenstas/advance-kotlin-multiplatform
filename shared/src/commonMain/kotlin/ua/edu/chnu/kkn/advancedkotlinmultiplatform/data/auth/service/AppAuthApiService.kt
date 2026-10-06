package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.request.LoginRequest
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.request.RefreshRequest
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.LoginResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response.RefreshResponse
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.Result
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.onSuccess
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.safeRequest
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.service.BASE_URL

internal class AppAuthApiService(
    private val client: HttpClient
) : AuthApiService {

    override suspend fun login(
        username: String,
        password: String
    ): Result<LoginResponse> {
        return client.safeRequest<LoginResponse> {
            post("$BASE_URL$AUTH$LOGIN") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }
        }.onSuccess {
            client.authProvider<BearerAuthProvider>()?.clearToken()
        }
    }

    override suspend fun refresh(refreshToken: String): Result<RefreshResponse> {
        return client.safeRequest {
            get("$BASE_URL$AUTH$REFRESH") {
                accept(ContentType.Application.Json)
                setBody(RefreshRequest(refreshToken))
            }
        }
    }
}
