package ua.edu.chnu.kkn.advancedkotlinmultiplatform.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import eu.anifantakis.lib.ksafe.KSafe

actual val platformModule = module {
    single { KSafe(androidApplication()) }
}
