package com.zhmu100.ma.ui.components.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendLabel
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.StackedBarChart
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBar
import co.yml.charts.ui.barchart.models.GroupBarChartData
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import com.zhmu100.ma.ui.data.NutrientsData
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.caloriesColor
import com.zhmu100.ma.ui.theme.carbsColor
import com.zhmu100.ma.ui.theme.fatsColor
import com.zhmu100.ma.ui.theme.proteinsColor


private val BAR_WIDTH = 25.dp
private val CHART_HEIGHT = 400.dp
private val LEGEND_HEIGHT = 30.dp
private const val Y_STEP_SIZE = 5

/**
 * Компонент стекированной вертикальной диаграммы для отображения различных категорий данных.
 *
 * @param data Список списков значений, где каждый элемент представляет группу столбцов,
 *             а внутренний список — значения для каждой категории в этом столбце.
 * @param labels Список названий категорий для легенды и подсказок.
 * @param colors Цвета для отображения соответствующих категорий.
 * @param xLabels Подписи для оси X (например, дни недели: ["Пн", "Вт", ...]).
 *                По умолчанию заданы как ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"].
 * @param modifier Модификатор для настройки внешнего вида и расположения компонента.
 *
 * ### Особенности:
 * - Отображает данные в виде стекированных столбцов.
 * - Включает интерактивную легенду с цветами и названиями категорий.
 * - Поддерживает подсказки при наведении или выборе элементов графика.
 * - Может использоваться для отображения питания, шагов, дистанции и других метрик.
 *
 * ### Пример использования:
 * ```kotlin
 * val nutritionData = listOf(
 *     listOf(10f, 20f, 5f, 200f),
 *     listOf(15f, 25f, 8f, 250f)
 * )
 *
 * StackableBarChart(
 *     data = nutritionData,
 *     labels = listOf("Белки", "Углеводы", "Жиры", "Калории"),
 *     colors = listOf(proteinsColor, carbsColor, fatsColor, caloriesColor),
 *     xLabels = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
 * )
 * ```
 */
@Composable
fun StackableBarChart(
    data: List<List<Float>>,
    labels: List<String>,
    colors: List<Color>,
    xLabels: List<String> = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"),
    modifier: Modifier = Modifier
) {
    val groupBarData = remember(data) {
        data.map { values ->
            GroupBar(
                "",
                values.mapIndexed { index, value ->
                    BarData(Point(index.toFloat(), value))
                }
            )
        }
    }

    VerticalStackedBarChart(
        groupBarData = groupBarData,
        labels = labels,
        colors = colors,
        xLabels = xLabels,
        modifier = modifier
    )
}

@Composable
private fun VerticalStackedBarChart(
    groupBarData: List<GroupBar>,
    labels: List<String>,
    colors: List<Color>,
    xLabels: List<String>,
    modifier: Modifier = Modifier
) {
    require(labels.size == colors.size) {
        "Labels and colors must be the same size"
    }

    val legendLabels = colors.mapIndexed { index, color ->
        LegendLabel(color, labels[index])
    }

    val xAxisData = AxisData.Builder()
        .steps(groupBarData.count() - 1)
        .labelAndAxisLinePadding(20.dp)
        .startDrawPadding(BAR_WIDTH)
        .labelData { index ->
            if (index < xLabels.size) xLabels[index] else ""
        }
        .build()

    val maxValue = groupBarData.maxOfOrNull { groupBar ->
        groupBar.barList.sumOf { it.point.y.toDouble() }.toFloat()
    } ?: 0f

    val yAxisData = AxisData.Builder()
        .steps(Y_STEP_SIZE)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index ->
            (index * (maxValue.toInt() / Y_STEP_SIZE)).toString()
        }
        .build()

    val legendsConfig = LegendsConfig(
        colorBoxSize = 7.dp,
        spaceBWLabelAndColorBox = 2.dp,
        legendLabelList = legendLabels,
        gridColumnCount = colors.size
    )

    val groupBarPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(
            barWidth = BAR_WIDTH,
            selectionHighlightData = SelectionHighlightData(
                highlightTextOffset = 5.dp,
                groupBarPopUpLabel = { x, value ->
                    "${labels.getOrNull(x.toInt()) ?: "Unknown"}: ${value.toInt()}"
                }
            )
        ),
        barColorPaletteList = colors
    )

    val groupBarChartData = GroupBarChartData(
        barPlotData = groupBarPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        drawBar = { drawScope, _, barStyle, drawOffset, height, barIndex ->
            with(drawScope) {
                drawRect(
                    color = colors[barIndex],
                    topLeft = drawOffset,
                    size = Size(barStyle.barWidth.toPx(), height),
                    style = barStyle.barDrawStyle,
                    blendMode = barStyle.barBlendMode
                )
            }
        }
    )

    Column(
        modifier.height(CHART_HEIGHT + LEGEND_HEIGHT).width(350.dp)
    ) {
        Legends(
            legendsConfig = legendsConfig,
            modifier = Modifier.height(LEGEND_HEIGHT)
        )
        StackedBarChart(
            modifier = Modifier,
            groupBarChartData = groupBarChartData
        )
    }
}

/**
 * Приватная функция преобразования данных о питательных веществах в формат для стекированной диаграммы.
 *
 * @param nutrients Список данных о питательных веществах
 * @return List<GroupBar> подготовленные данные для отображения на диаграмме
 *
 * Особенности преобразования:
 * - Создает группу столбцов для каждого набора данных
 * - Каждый столбец содержит 4 значения (белки, жиры, углеводы, калории)
 * - Использует фиксированные индексы для категорий (0-3)
 */
private fun nutrientsToGroupBar(nutrients: List<NutrientsData>): List<GroupBar> {
    return nutrients.map { nutrition ->
        GroupBar(
            "",
            listOf(
                BarData(Point(0f, nutrition.proteins)),
                BarData(Point(1f, nutrition.fats)),
                BarData(Point(2f, nutrition.carbs)),
                BarData(Point(3f, nutrition.calories))
            )
        )
    }
}

/**
 * Приватная функция преобразования индекса категории в читаемое название.
 *
 * @param x Индекс категории в виде строки
 * @return String читаемое название категории
 * @throws IllegalArgumentException при передаче недопустимого индекса
 */
private fun xToName(x: String): String {
    return when (x) {
        "0" -> "Белки"
        "1" -> "Жиры"
        "2" -> "Углеводы"
        "3" -> "Калории"
        else -> throw IllegalArgumentException("No such nutrient")
    }
}

@Preview(showBackground = true)
@Composable
private fun StackableNutrientsPreview() {
    MATheme {
        val nutrients = listOf(
            NutrientsData(proteins = 10f, carbs = 20f, fats = 5f, calories = 200f),
            NutrientsData(proteins = 10f, carbs = 20f, fats = 5f, calories = 200f),
            NutrientsData(proteins = 10f, carbs = 20f, fats = 5f, calories = 200f),
            NutrientsData(proteins = 10f, carbs = 20f, fats = 5f, calories = 200f),
            NutrientsData(proteins = 10f, carbs = 20f, fats = 5f, calories = 200f),
            NutrientsData(proteins = 10f, carbs = 20f, fats = 5f, calories = 200f),
            NutrientsData(proteins = 15f, carbs = 25f, fats = 8f, calories = 250f)
        )

        val nutritionData = nutrients.map {
            listOf(it.proteins, it.carbs, it.fats, it.calories)
        }

        StackableBarChart(
            data = nutritionData,
            labels = listOf("Белки", "Углеводы", "Жиры", "Калории"),
            colors = listOf(proteinsColor, carbsColor, fatsColor, caloriesColor)
        )
    }
}