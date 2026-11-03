package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation.ui.ObserveEvents
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.Res
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.auth_caption
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.auth_hint
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.auth_title
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.password_label
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.sign_in
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.username_label

@Composable
internal fun AuthScreen(
    onAuthenticated: () -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val passwordFocus = remember { FocusRequester() }

    ObserveEvents(viewModel.events) { event ->
        when (event) {
            AuthEvent.Authenticated -> onAuthenticated()
            is AuthEvent.ShowError -> scope.launch { snackbar.showSnackbar(event.message) }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbar) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .widthIn(max = 460.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(Res.string.auth_caption),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.auth_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(Res.string.auth_hint),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.68f),
            )
            Spacer(Modifier.height(34.dp))
            OutlinedTextField(
                value = state.username,
                onValueChange = { viewModel.onAction(AuthAction.UsernameChanged(it)) },
                label = { Text(stringResource(Res.string.username_label)) },
                singleLine = true,
                isError = state.usernameError != null,
                supportingText = state.usernameError?.let { { Text(it) } },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onAction(AuthAction.PasswordChanged(it)) },
                label = { Text(stringResource(Res.string.password_label)) },
                singleLine = true,
                isError = state.passwordError != null,
                supportingText = state.passwordError?.let { { Text(it) } },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                ),
                keyboardActions = KeyboardActions(onDone = { viewModel.onAction(AuthAction.Submit) }),
                modifier = Modifier.fillMaxWidth().focusRequester(passwordFocus),
            )
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { viewModel.onAction(AuthAction.Submit) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().height(54.dp),
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(22.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Text(stringResource(Res.string.sign_in))
                }
            }
        }
    }
}
