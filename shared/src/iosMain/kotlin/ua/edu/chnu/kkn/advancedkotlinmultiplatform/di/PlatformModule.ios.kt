package ua.edu.chnu.kkn.advancedkotlinmultiplatform.di

import eu.anifantakis.lib.ksafe.KSafe
import org.koin.dsl.module

actual val platformModule = module {
    single { KSafe() }
}
