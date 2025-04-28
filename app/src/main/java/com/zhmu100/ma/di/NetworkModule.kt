package com.zhmu100.ma.di

import com.zhmu100.ma.domain.Network
import com.zhmu100.ma.domain.api.diet.DietApi
import com.zhmu100.ma.domain.api.diet.DietApiImpl
import com.zhmu100.ma.domain.api.diet.DietApiMock
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.files.FilesApiImpl
import com.zhmu100.ma.domain.api.files.FilesApiMock
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.api.profile.ProfileApiImpl
import com.zhmu100.ma.domain.api.profile.ProfileApiMock
import com.zhmu100.ma.domain.api.statistic.StatisticsApi
import com.zhmu100.ma.domain.api.statistic.StatisticsApiImpl
import org.koin.dsl.module

val networkModule = module {
    single { Network.httpClient }
//    single<ProfileApi> { ProfileApiImpl(get(),  "http://localhost:8080/api/profiles") }
//    single<FilesApi> { FilesApiImpl(get(),  "http://localhost:8080/api/profiles") }
//    single<DietApi> { DietApiImpl(get(),  "http://localhost:8080/api/diet") }

    single<ProfileApi> { ProfileApiMock() }
    single<FilesApi> { FilesApiMock() }
    single<StatisticsApi> { StatisticsApiImpl(get(), "http://localhost:8080/api/statistics") }
    single<DietApi> { DietApiMock() }
}