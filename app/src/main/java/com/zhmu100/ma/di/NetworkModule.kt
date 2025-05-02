package com.zhmu100.ma.di

import com.zhmu100.ma.domain.Network
import com.zhmu100.ma.domain.api.diet.DietApi
import com.zhmu100.ma.domain.api.diet.DietApiImpl
import com.zhmu100.ma.domain.api.diet.DietApiMock
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.files.FilesApiImpl
import com.zhmu100.ma.domain.api.files.FilesApiMock
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
//    single<DietApi> { DietApiImpl(get(),  "http://localhost:8080/api/diet") }

    single<ProfileApi> { ProfileApiMock() }
    single<FilesApi> { FilesApiMock() }
    single<TrainingApi> { TrainingApiMock() }
    single<DietApi> { DietApiMock() }
    single<NoteApi> { NoteApiImpl(get(), "http://localhost:8080/api/notebook") }
    single<StatisticsApi> { StatisticsApiMock() }
}