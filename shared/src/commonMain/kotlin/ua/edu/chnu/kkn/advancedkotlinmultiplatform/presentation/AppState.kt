package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.runtime.Immutable
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.model.responses.Post

@Immutable
internal data class AppState(
    val isProgressVisible: Boolean = false,
    val posts: List<Post> = emptyList(),
    val result: String? = null,
)
