package com.zhmu100.ma.di

import com.zhmu100.ma.domain.viewModel.DevicesViewModel
import com.zhmu100.ma.domain.viewModel.ProfileViewModel
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
    viewModel { TrainingViewModel(get(), get()) }
    viewModel { TrainingMapViewModel(get()) }
    viewModel { TrainingGymViewModel(get()) }
    viewModel { TrainingHistoryViewModel(get(), get()) }
    viewModel { StatisticViewModel(get(), get()) }
    viewModel { DevicesViewModel(get()) }
}