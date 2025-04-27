package com.zhmu100.ma.di

import com.zhmu100.ma.domain.storage.SettingsStorage
import com.zhmu100.ma.domain.storage.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single<SettingsStorage> {
        SettingsStorage.apply {
            init(androidContext())
        }
    }

    single<TokenStorage> {
        TokenStorage.apply {
            init(androidContext())
        }
    }
}