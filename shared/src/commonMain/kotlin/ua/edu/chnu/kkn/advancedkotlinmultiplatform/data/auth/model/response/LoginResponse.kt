package ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val refreshToken: String,
    val username: String
)
