package com.zhmu100.ma.di

import com.zhmu100.ma.domain.Network
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.files.FilesApiImpl
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.api.profile.ProfileApiImpl
import org.koin.dsl.module

val networkModule = module {
    single { Network.httpClient }
    single<ProfileApi> { ProfileApiImpl(get(),  "http://localhost:8080/api/profiles") }
    single<FilesApi> { FilesApiImpl(get(),  "http://localhost:8080/api/profiles") }
}