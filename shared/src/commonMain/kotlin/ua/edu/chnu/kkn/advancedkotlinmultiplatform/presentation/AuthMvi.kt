package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.auth.login.LoginUseCase

internal data class AuthState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
)

internal sealed interface AuthAction {
    data class UsernameChanged(val value: String) : AuthAction
    data class PasswordChanged(val value: String) : AuthAction
    data object Submit : AuthAction
}

internal sealed interface AuthEvent {
    data object Authenticated : AuthEvent
    data class ShowError(val message: String) : AuthEvent
}

internal class AuthViewModel(
    private val login: LoginUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _events = Channel<AuthEvent>(Channel.BUFFERED)
    val events: Flow<AuthEvent> = _events.receiveAsFlow()

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.UsernameChanged -> _state.update {
                it.copy(username = action.value, usernameError = null)
            }
            is AuthAction.PasswordChanged -> _state.update {
                it.copy(password = action.value, passwordError = null)
            }
            AuthAction.Submit -> submit()
        }
    }

    private fun submit() {
        val current = _state.value
        val usernameError = if (current.username.trim().isEmpty()) "Enter a username" else null
        val passwordError = when {
            current.password.isEmpty() -> "Enter a password"
            current.password.length < 6 -> "Password must contain at least 6 characters"
            else -> null
        }
        if (usernameError != null || passwordError != null) {
            _state.update { it.copy(usernameError = usernameError, passwordError = passwordError) }
            return
        }

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            login(current.username.trim(), current.password)
                .onSuccess { _events.send(AuthEvent.Authenticated) }
                .onFailure { _events.send(AuthEvent.ShowError(it)) }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
