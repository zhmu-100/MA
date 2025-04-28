package com.zhmu100.ma.di

import com.zhmu100.ma.domain.viewModel.LoginViewModel
import com.zhmu100.ma.domain.viewModel.NotificationViewModel
import com.zhmu100.ma.domain.viewModel.NoteViewModel
import com.zhmu100.ma.domain.viewModel.ProfileViewModel
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
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { NotificationViewModel(get()) }
    viewModel { NoteViewModel(get()) }
    viewModel { TrainingViewModel(get(), get()) }
    viewModel { TrainingMapViewModel() }
    viewModel { TrainingGymViewModel() }
    viewModel { TrainingHistoryViewModel(get(), get()) }
    viewModel { StatisticViewModel(get(), get()) }
}