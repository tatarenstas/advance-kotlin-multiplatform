package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.Res
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.app_name
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.get_started
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.next
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.onboarding_label
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.skip
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_1_body
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_1_kicker
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_1_title
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_2_body
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_2_kicker
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_2_title
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_3_body
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_3_kicker
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_3_title
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_4_body
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_4_kicker
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.slide_4_title

private data class Slide(
    val kicker: StringResource,
    val title: StringResource,
    val body: StringResource,
    val accent: Color,
)

private val slides = listOf(
    Slide(Res.string.slide_1_kicker, Res.string.slide_1_title, Res.string.slide_1_body, Mint),
    Slide(Res.string.slide_2_kicker, Res.string.slide_2_title, Res.string.slide_2_body, Coral),
    Slide(Res.string.slide_3_kicker, Res.string.slide_3_title, Res.string.slide_3_body, Color(0xFFD7CAED)),
    Slide(Res.string.slide_4_kicker, Res.string.slide_4_title, Res.string.slide_4_body, Color(0xFFF2D792)),
)

@Composable
internal fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { slides.size })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .widthIn(max = 540.dp)
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = stringResource(Res.string.onboarding_label),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.66f),
                    )
                }
                TextButton(onClick = onFinish) {
                    Text(stringResource(Res.string.skip))
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).fillMaxWidth(),
            ) { page ->
                val slide = slides[page]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    TaskArtwork(page = page, accent = slide.accent)
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = stringResource(slide.kicker),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 2.sp,
                    )
                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = stringResource(slide.title),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp,
                        modifier = Modifier.fillMaxWidth().widthIn(max = 340.dp),
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(slide.body),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.72f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().widthIn(max = 340.dp),
                    )
                }
            }

            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                repeat(slides.size) { index ->
                    Box(
                        Modifier
                            .width(if (index == pagerState.currentPage) 28.dp else 8.dp)
                            .height(8.dp)
                            .background(
                                if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline.copy(alpha = 0.45f),
                                CircleShape,
                            )
                    )
                }
            }
            Button(
                onClick = {
                    if (pagerState.currentPage == slides.lastIndex) onFinish()
                    else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Text(
                    if (pagerState.currentPage == slides.lastIndex) stringResource(Res.string.get_started)
                    else stringResource(Res.string.next),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun TaskArtwork(page: Int, accent: Color) {
    Box(
        modifier = Modifier.fillMaxWidth().height(260.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .offset(x = 65.dp, y = (-38).dp)
                .size(150.dp)
                .background(accent.copy(alpha = 0.65f), CircleShape)
        )
        Box(
            Modifier
                .offset(x = (-100).dp, y = 74.dp)
                .size(90.dp)
                .background(Coral.copy(alpha = 0.54f), CircleShape)
        )
        Column(
            modifier = Modifier
                .width(250.dp)
                .rotate(if (page % 2 == 0) -5f else 5f)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.18f), RoundedCornerShape(24.dp))
                .padding(22.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(10.dp).background(accent, CircleShape))
                Spacer(Modifier.width(9.dp))
                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Spacer(Modifier.height(20.dp))
            repeat(3) { index ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 14.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(23.dp)
                            .background(
                                if (index <= page % 3) accent else Color.Transparent,
                                RoundedCornerShape(7.dp),
                            )
                            .border(1.dp, accent, RoundedCornerShape(7.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (index <= page % 3) {
                            Canvas(Modifier.size(13.dp)) {
                                drawLine(
                                    color = Ink,
                                    start = Offset(size.width * 0.1f, size.height * 0.52f),
                                    end = Offset(size.width * 0.42f, size.height * 0.82f),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round,
                                )
                                drawLine(
                                    color = Ink,
                                    start = Offset(size.width * 0.42f, size.height * 0.82f),
                                    end = Offset(size.width * 0.92f, size.height * 0.14f),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round,
                                )
                            }
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Box(
                        Modifier
                            .width((124 - index * 20).dp)
                            .height(9.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.14f), CircleShape)
                    )
                }
            }
        }
        Box(
            Modifier
                .offset(x = (-135).dp, y = (-93).dp)
                .size(16.dp)
                .background(MaterialTheme.colorScheme.secondary, CircleShape)
        )
    }
}
