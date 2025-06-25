package com.zhmu100.ma.di

import com.zhmu100.ma.domain.Network
import com.zhmu100.ma.domain.api.auth.AuthApi
import com.zhmu100.ma.domain.api.auth.AuthApiImpl
import com.zhmu100.ma.domain.api.comments.CommentApi
import com.zhmu100.ma.domain.api.comments.CommentApiImpl
import com.zhmu100.ma.domain.api.diet.DietApi
import com.zhmu100.ma.domain.api.diet.DietApiImpl
import com.zhmu100.ma.domain.api.feed.PostApi
import com.zhmu100.ma.domain.api.feed.PostApiImpl
import com.zhmu100.ma.domain.api.files.FilesApi
import com.zhmu100.ma.domain.api.files.FilesApiImpl
import com.zhmu100.ma.domain.api.notes.NoteApi
import com.zhmu100.ma.domain.api.notes.NoteApiImpl
import com.zhmu100.ma.domain.api.notification.NotificationApi
import com.zhmu100.ma.domain.api.notification.NotificationApiImpl
import com.zhmu100.ma.domain.api.profile.ProfileApi
import com.zhmu100.ma.domain.api.profile.ProfileApiImpl
import com.zhmu100.ma.domain.api.reaction.ReactionApiImpl
import com.zhmu100.ma.domain.api.reactions.ReactionApi
import com.zhmu100.ma.domain.api.statistic.StatisticsApi
import com.zhmu100.ma.domain.api.statistic.StatisticsApiImpl
import com.zhmu100.ma.domain.api.training.TrainingApi
import com.zhmu100.ma.domain.api.training.TrainingApiImpl
import org.koin.dsl.module


/**
 * Модуль DI для реализаций API
 */
val networkModule = module {

    single { Network.init(get()) }
    single<AuthApi> { AuthApiImpl(get(), "http://188.225.77.13:8080/api/auth") }
    single<ProfileApi> { ProfileApiImpl(get(),  "http://188.225.77.13:8083/profiles", get()) }
//    single<ProfileApi> { ProfileApiMock() }
    single<TrainingApi> { TrainingApiImpl(get(), "http://188.225.77.13:8084/training", get()) }
//    single<TrainingApi> { TrainingApiMock() }
    single<CommentApi> { CommentApiImpl(get(), "http://188.225.77.13:8085") }
    single<PostApi> { PostApiImpl(get(), get(), "http://188.225.77.13:8085") }
    single<ReactionApi> { ReactionApiImpl(get(), "http://188.225.77.13:8085") }
    single<NoteApi> { NoteApiImpl(get(), "http://188.225.77.13:8086/notebook") }
    single<NotificationApi> { NotificationApiImpl(get(), "http://188.225.77.13:8086/notebook") }
    single<DietApi> { DietApiImpl(get(),  "http://188.225.77.13:8087/diet", get()) }
//    single<DietApi> { DietApiMock() }
    single<StatisticsApi> { StatisticsApiImpl(get(), "http://188.225.77.13:8088/api/statistics") }
//    single<StatisticsApi> { StatisticsApiMock() }
    single<FilesApi> { FilesApiImpl(get(),  "http://188.225.77.13:8089/files") }
//    single<FilesApi> { FilesApiMock() }
}