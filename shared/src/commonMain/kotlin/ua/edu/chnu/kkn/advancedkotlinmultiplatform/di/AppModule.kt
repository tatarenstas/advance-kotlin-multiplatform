package ua.edu.chnu.kkn.advancedkotlinmultiplatform.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.AppAuthRepository
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service.AppAuthApiService
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service.AuthApiService
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service.LOGIN
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.service.REFRESH
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.onFailure
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.onSuccess
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.service.AppPostApiService
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.service.PostApiService
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.AppPostRepository
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.security.AppSecureStorage
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.security.SecureStorage
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.AuthRepository
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.login.LoginUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.PostRepository
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.create.CreatePostUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.edit.EditPostUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.obtain.ObtainPostsUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.remove.RemovePostUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation.AppViewModel
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation.UserPreferences

val secureModule = module {
    includes(platformModule)
    singleOf(::AppSecureStorage) { bind<SecureStorage>() }
}

val networkModule = module {
    includes(secureModule)
    single {
        val secureStorage: SecureStorage = get()
        HttpClient {
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.withTag("Ktor").d { message }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = secureStorage.getAccessToken(),
                            refreshToken = secureStorage.getRefreshToken()
                        )
                    }
                    refreshTokens {
                        val authRepository: AuthRepository = get()
                        authRepository.refresh(secureStorage.getRefreshToken())
                            .onSuccess {
                                secureStorage.setAccessToken(it.accessToken)
                                secureStorage.setRefreshToken(it.refreshToken)
                            }.onFailure {
                                //TODO Handle refresh token failure, e.g., log out the user or show an error message
                            }
                        BearerTokens(
                            secureStorage.getAccessToken(),
                            secureStorage.getRefreshToken()
                        )
                    }
                    sendWithoutRequest { request ->
                        val path = request.url.encodedPath
                        !path.endsWith(LOGIN) && !path.endsWith(REFRESH)
                    }
                }
            }
        }
    }
    singleOf(::AppAuthApiService) { bind<AuthApiService>() }
    singleOf(::AppAuthRepository) { bind<AuthRepository>() }
    singleOf(::AppPostApiService) { bind<PostApiService>() }
    singleOf(::AppAuthRepository) { bind<AuthRepository>() }
}
val appModule = module {
    includes(networkModule, platformModule)
    single<Settings> { Settings() }
    singleOf(::UserPreferences)
    singleOf(::AppPostRepository) { bind<PostRepository>() }
    factoryOf(::LoginUseCase)
    factoryOf(::CreatePostUseCase)
    factoryOf(::EditPostUseCase)
    factoryOf(::ObtainPostsUseCase)
    factoryOf(::RemovePostUseCase)
    viewModelOf(::AppViewModel)
}
