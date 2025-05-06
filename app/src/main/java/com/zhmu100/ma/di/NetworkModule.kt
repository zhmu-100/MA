package com.zhmu100.ma.di

import com.zhmu100.ma.domain.Network
import com.zhmu100.ma.domain.api.auth.AuthApi
import com.zhmu100.ma.domain.api.auth.AuthApiImpl
import com.zhmu100.ma.domain.api.diet.DietApi
import com.zhmu100.ma.domain.api.diet.DietApiImpl
import com.zhmu100.ma.domain.api.diet.DietApiMock
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.files.FilesApiImpl
import com.zhmu100.ma.domain.api.files.FilesApiMock
import com.zhmu100.ma.domain.api.notification.NotificationApi
import com.zhmu100.ma.domain.api.notification.NotificationApiImpl
import com.zhmu100.ma.domain.api.notes.NoteApi
import com.zhmu100.ma.domain.api.notes.NoteApiImpl
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.api.profile.ProfileApiImpl
import com.zhmu100.ma.domain.api.profile.ProfileApiMock
import com.zhmu100.ma.domain.api.statistic.StatisticsApi
import com.zhmu100.ma.domain.api.statistic.StatisticsApiMock
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.api.training.TrainingApiMock
import org.koin.dsl.module


/**
 * Модуль DI для реализаций API
 */
val networkModule = module {
    single { Network.httpClient }
//    single<ProfileApi> { ProfileApiImpl(get(),  "http://localhost:8080/api/profiles") }
//    single<FilesApi> { FilesApiImpl(get(),  "http://localhost:8080/api/profiles") }
//    single<TrainingApi> { TrainingApiImpl(get(), "http://localhost:8080/api/training") }
//    single<DietApi> { DietApiImpl(get(),  "http://localhost:8080/api/diet") }
//    single<StatisticsApi> { StatisticsApiImpl(get(), "http://localhost:8080/api/statistics") }

    single<ProfileApi> { ProfileApiMock() }
    single<FilesApi> { FilesApiMock() }
    single<TrainingApi> { TrainingApiMock() }
    single<DietApi> { DietApiMock() }
    single<AuthApi> { AuthApiImpl(get(), "http://188.225.77.13:8080/api/auth") }
    single<NotificationApi> { NotificationApiImpl(get(), "http://localhost:8080/api/notebook") }
    single<NoteApi> { NoteApiImpl(get(), "http://localhost:8080/api/notebook") }
    single<StatisticsApi> { StatisticsApiMock() }
}