package ua.edu.chnu.kkn.advancedkotlinmultiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.di.initKoin
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation.App

fun main() = application {
    initKoin { printLogger() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Daymark",
    ) {
        App()
    }
}
