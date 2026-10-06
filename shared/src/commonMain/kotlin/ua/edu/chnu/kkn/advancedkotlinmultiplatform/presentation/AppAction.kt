package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

sealed class AppAction {
    data object OnLogin : AppAction()
    data object OnFetchPosts : AppAction()
    data object OnCreatePost : AppAction()
    data object OnUpdatePost : AppAction()
    data object OnDeletePost : AppAction()
}
