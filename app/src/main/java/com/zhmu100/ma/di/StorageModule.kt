package com.zhmu100.ma.di

import com.zhmu100.ma.domain.storage.DeviceStorage
import com.zhmu100.ma.domain.storage.MessageManager
import com.zhmu100.ma.domain.storage.NotificationStorage
import com.zhmu100.ma.domain.storage.SettingsStorage
import com.zhmu100.ma.domain.storage.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Модуль DI для объектов локального хранения данных
 */
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
    single<DeviceStorage> {
        DeviceStorage
    }
    single<NotificationStorage> {
        NotificationStorage.apply {
            init(androidContext())
        }
    }
    single<MessageManager> { MessageManager() }
}