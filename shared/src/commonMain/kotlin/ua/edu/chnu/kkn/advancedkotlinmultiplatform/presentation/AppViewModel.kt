package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.onFailure
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.common.onSuccess
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.model.requests.NewPost
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.data.posts.model.responses.Reactions
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.login.LoginUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.create.CreatePostUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.edit.EditPostUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.obtain.ObtainPostsUseCase
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.posts.remove.RemovePostUseCase

@Stable
class AppViewModel internal constructor(
    private val createPostUseCase: CreatePostUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val obtainPostsUseCase: ObtainPostsUseCase,
    private val removePostUseCase: RemovePostUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    internal val state: StateFlow<AppState> = _state.asStateFlow()

    private val _events = Channel<AppEvent>(capacity = Channel.BUFFERED)
    val events: Flow<AppEvent> = _events.receiveAsFlow()

    init {
        onAction(AppAction.OnFetchPosts)
    }

    fun onAction(action: AppAction) {
        if (_state.value.isProgressVisible) return

        _state.update { it.copy(isProgressVisible = true, result = null) }
        viewModelScope.launch {
            try {
                when (action) {
                    AppAction.OnLogin -> login()
                    AppAction.OnFetchPosts -> fetchPosts()
                    AppAction.OnCreatePost -> createPost()
                    AppAction.OnUpdatePost -> updatePost()
                    AppAction.OnDeletePost -> deletePost()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                showError(e.message ?: "Unexpected error")
            } finally {
                _state.update { it.copy(isProgressVisible = false) }
            }
        }
    }

    private suspend fun login() {
        loginUseCase("emilys", "emilyspass")
            .onSuccess { _state.update { state -> state.copy(result = "Login successful") } }
            .onFailure(::showError)
    }

    private suspend fun fetchPosts() {
        obtainPostsUseCase()
            .onSuccess { posts -> _state.update { it.copy(posts = posts.posts, result = posts.toString()) } }
            .onFailure(::showError)
    }

    private suspend fun createPost() {
        createPostUseCase(createNewPost())
            .onSuccess { result -> _state.update { it.copy(result = result) } }
            .onFailure(::showError)
    }

    private suspend fun updatePost() {
        val post = _state.value.posts.firstOrNull()
        if (post == null) {
            showError("Fetch posts before updating one")
            return
        }
        editPostUseCase(post.copy(body = "Updated body"))
            .onSuccess { result -> _state.update { it.copy(result = result) } }
            .onFailure(::showError)
    }

    private suspend fun deletePost() {
        val post = _state.value.posts.firstOrNull()
        if (post == null) {
            showError("Fetch posts before deleting one")
            return
        }
        removePostUseCase(post.id)
            .onSuccess { result -> _state.update { it.copy(result = result) } }
            .onFailure(::showError)
    }

    private fun showError(message: String) {
        _events.trySend(AppEvent.ShowErrorSnackbar(message))
    }

    private fun createNewPost() = NewPost(
        body = "Body text",
        reactions = Reactions(),
        tags = listOf("Tag 1", "Tag 2"),
        title = "Title text",
        userId = 5,
    )
}
