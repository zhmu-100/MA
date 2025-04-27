package com.zhmu100.ma.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhmu100.ma.domain.api.statistic.StatisticsApi
import com.zhmu100.ma.domain.model.statistic.CaloriesData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val statisticsApi: StatisticsApi
) : ViewModel() {

    private val _caloriesState =
        MutableStateFlow<ViewState<List<CaloriesData>>>(ViewState.Uninitialized)
    val caloriesState = _caloriesState.asStateFlow()


    // Получить все данные статистики
    fun loadAllStatistics(userId: String) {
        if (_caloriesState.value is ViewState.Loading) {
            return
        }

        _caloriesState.value = ViewState.Loading

        viewModelScope.launch {
            runCatching {
                statisticsApi.getCaloriesData(userId)
            }.onSuccess {
                _caloriesState.value = ViewState.Success(it)
            }.onFailure {
                _caloriesState.value =
                    ViewState.Error("Ошибка загрузки статистики: ${it.message}", it)
            }
        }
    }

}
