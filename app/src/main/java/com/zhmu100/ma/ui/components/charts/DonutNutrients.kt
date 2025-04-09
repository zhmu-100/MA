package com.zhmu100.ma.ui.components.charts

import android.graphics.Typeface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendLabel
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.zhmu100.ma.ui.data.NutrientsData
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.caloriesColor
import com.zhmu100.ma.ui.theme.carbsColor
import com.zhmu100.ma.ui.theme.fatsColor
import com.zhmu100.ma.ui.theme.proteinsColor

private val COLOR_PALETTE = listOf(caloriesColor, carbsColor, fatsColor, proteinsColor)
private val LABELS = listOf("Белки", "Углеводы", "Жиры", "Калории")

/**
 * Компонент кольцевой диаграммы (donut chart) для отображения питательных веществ.
 *
 * @param nutrients Данные о питательных веществах для отображения
 * @param modifier Модификатор для настройки внешнего вида и расположения компонента
 *
 * Внутренняя реализация:
 * - Использует библиотеку для построения кольцевых диаграмм
 * - Отображает 4 категории: белки, жиры, углеводы и калории
 * - Включает легенду с цветовыми обозначениями
 * - Поддерживает анимации и пользовательские настройки отображения
 */
@Composable
fun DonutNutrients(nutrients: NutrientsData, modifier: Modifier = Modifier) {
    SimpleDonutChart(nutrients, modifier)
}

@Composable
private fun SimpleDonutChart(nutrients: NutrientsData, modifier: Modifier = Modifier) {
    val legendLabels = COLOR_PALETTE.mapIndexed { index, color ->
        LegendLabel(color, LABELS[index])
    }
    val donutChartData = nutrientsToPieData(nutrients)
    val pieChartConfig = PieChartConfig(
        labelVisible = true,
        strokeWidth = 100f,
        labelColor = Color.Black,
        activeSliceAlpha = .9f,
        labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
        isAnimationEnable = true,
        chartPadding = 25,
        labelFontSize = 30.sp,
        isSumVisible = true,
    )
    val legendsConfig = LegendsConfig(
        gridColumnCount = 1,
        colorBoxSize = 8.dp,
        spaceBWLabelAndColorBox = 4.dp,
        legendLabelList = legendLabels,
        legendsArrangement = Arrangement.Start
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        DonutPieChart(
            modifier = Modifier
                .height(250.dp),
            donutChartData,
            pieChartConfig
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}

/**
 * Приватная функция преобразования данных о питательных веществах в формат для диаграммы.
 *
 * @param nutrients Данные о питательных веществах
 * @return PieChartData подготовленные данные для отображения на диаграмме
 */
private fun nutrientsToPieData(nutrients: NutrientsData): PieChartData {
    return PieChartData(
        plotType = PlotType.Donut,
        slices = listOf(
            PieChartData.Slice(label = "Белки", value = nutrients.carbs, color = proteinsColor),
            PieChartData.Slice(label = "Жиры", value = nutrients.fats, color = fatsColor),
            PieChartData.Slice(label = "Углеводы", value = nutrients.carbs, color = carbsColor),
            PieChartData.Slice(label = "Калории", value = nutrients.calories, color = caloriesColor)
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun DonutNutrientsPreview() {
    MATheme {
        val nutritionData = NutrientsData(100f, 30f, 30f, 100f)
        DonutNutrients(nutritionData)
    }
}