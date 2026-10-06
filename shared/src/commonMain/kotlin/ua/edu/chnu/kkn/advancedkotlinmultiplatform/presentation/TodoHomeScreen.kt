package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.Res
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.api_demo
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.app_name
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.appearance
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.back_to_tasks
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.dark_mode
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.demo_task_1
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.demo_task_2
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.demo_task_3
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.home_heading
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.home_subtitle
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.preview_note
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.replay_intro
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.tasks_done
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.today
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.your_day

@Composable
internal fun TodoHomeScreen(
    useDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onReplayIntro: () -> Unit,
    onOpenApiDemo: () -> Unit,
) {
    val checked = remember { mutableStateListOf(false, false, false) }
    val titles = listOf(
        stringResource(Res.string.demo_task_1),
        stringResource(Res.string.demo_task_2),
        stringResource(Res.string.demo_task_3),
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .widthIn(max = 620.dp)
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(14.dp).background(Mint, CircleShape))
                Text(
                    text = "  " + stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Spacer(Modifier.height(42.dp))
            Text(
                text = stringResource(Res.string.your_day).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = 2.sp,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.home_heading),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 42.sp,
            )
            Spacer(Modifier.height(9.dp))
            Text(
                text = stringResource(Res.string.home_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.66f),
            )
            Spacer(Modifier.height(30.dp))
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            stringResource(Res.string.today),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            letterSpacing = 2.sp,
                        )
                        Text(
                            stringResource(Res.string.tasks_done, checked.count { it }, checked.size),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    titles.forEachIndexed { index, title ->
                        if (index > 0) HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                        Row(
                            modifier = Modifier.fillMaxWidth().height(62.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = checked[index],
                                onCheckedChange = { checked[index] = it },
                            )
                            Text(
                                title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (checked[index]) 0.48f else 1f),
                                textDecoration = if (checked[index]) TextDecoration.LineThrough else null,
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(
                stringResource(Res.string.preview_note),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.58f),
            )
            Spacer(Modifier.height(32.dp))
            Text(
                stringResource(Res.string.appearance),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Row(
                modifier = Modifier.fillMaxWidth().height(58.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(stringResource(Res.string.dark_mode), style = MaterialTheme.typography.bodyLarge)
                Switch(checked = useDarkTheme, onCheckedChange = onDarkThemeChange)
            }
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onReplayIntro) {
                Text(stringResource(Res.string.replay_intro))
            }
            Button(onClick = onOpenApiDemo, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(Res.string.api_demo))
            }
        }
    }
}

@Composable
internal fun ApiDemoBackButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(stringResource(Res.string.back_to_tasks))
    }
}
