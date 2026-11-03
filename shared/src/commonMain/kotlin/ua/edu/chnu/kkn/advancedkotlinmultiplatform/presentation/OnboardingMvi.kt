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
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.domain.onboarding.CompleteOnboardingUseCase

internal data class OnboardingState(
    val isCompleting: Boolean = false,
)

internal sealed interface OnboardingAction {
    data object Complete : OnboardingAction
}

internal sealed interface OnboardingEvent {
    data object Completed : OnboardingEvent
}

internal class OnboardingViewModel(
    private val completeOnboarding: CompleteOnboardingUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    private val _events = Channel<OnboardingEvent>(Channel.BUFFERED)
    val events: Flow<OnboardingEvent> = _events.receiveAsFlow()

    fun onAction(action: OnboardingAction) {
        if (action != OnboardingAction.Complete || _state.value.isCompleting) return
        _state.update { it.copy(isCompleting = true) }
        viewModelScope.launch {
            completeOnboarding()
            _state.update { it.copy(isCompleting = false) }
            _events.send(OnboardingEvent.Completed)
        }
    }
}
