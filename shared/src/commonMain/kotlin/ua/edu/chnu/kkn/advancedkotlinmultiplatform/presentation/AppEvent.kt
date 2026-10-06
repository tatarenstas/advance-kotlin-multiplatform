package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

sealed interface AppEvent {
    data class ShowErrorSnackbar(val message: String) : AppEvent
}
