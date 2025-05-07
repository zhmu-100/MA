package com.zhmu100.ma.di

import com.zhmu100.ma.domain.viewModel.CommentViewModel
import com.zhmu100.ma.domain.viewModel.AuthViewModel
import com.zhmu100.ma.domain.viewModel.DevicesViewModel
import com.zhmu100.ma.domain.viewModel.DietViewModel
import com.zhmu100.ma.domain.viewModel.FollowerViewModel
import com.zhmu100.ma.domain.viewModel.LoginViewModel
import com.zhmu100.ma.domain.viewModel.NotificationViewModel
import com.zhmu100.ma.domain.viewModel.NoteViewModel
import com.zhmu100.ma.domain.viewModel.PostViewModel
import com.zhmu100.ma.domain.viewModel.ProfileViewModel
import com.zhmu100.ma.domain.viewModel.ReactionViewModel
import com.zhmu100.ma.domain.viewModel.RegisterViewModel
import com.zhmu100.ma.domain.viewModel.SettingsViewModel
import com.zhmu100.ma.domain.viewModel.StatisticViewModel
import com.zhmu100.ma.domain.viewModel.TrainingGymViewModel
import com.zhmu100.ma.domain.viewModel.TrainingHistoryViewModel
import com.zhmu100.ma.domain.viewModel.TrainingMapViewModel
import com.zhmu100.ma.domain.viewModel.TrainingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Модуль DI для ViewModel
 */
val viewModelModule = module {
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { NotificationViewModel(get(), get()) }
    viewModel { NoteViewModel(get(), get()) }
    viewModel { TrainingViewModel(get(), get()) }
    viewModel { TrainingMapViewModel(get()) }
    viewModel { TrainingGymViewModel(get()) }
    viewModel { TrainingHistoryViewModel(get(), get()) }
    viewModel { StatisticViewModel(get(), get()) }
    viewModel { DevicesViewModel(get()) }
    viewModel { DietViewModel(get()) }
    viewModel { PostViewModel(get(), get(), get(), get()) }
    viewModel { CommentViewModel(get(), get(), get()) }
    viewModel { ReactionViewModel(get(), get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
}