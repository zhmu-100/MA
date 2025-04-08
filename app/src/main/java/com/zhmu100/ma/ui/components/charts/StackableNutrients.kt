package com.zhmu100.ma.ui.components.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
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


private val BAR_WIDTH = 30.dp
private val CHART_HEIGHT = 400.dp
private val LEGEND_HEIGHT = 30.dp
private val Y_STEP_SIZE = 5
private val COLOR_PALETTE = listOf(caloriesColor, carbsColor, fatsColor, proteinsColor)
private val LABELS = listOf("Белки", "Углеводы", "Жиры", "Калории")

@Composable
fun StackableNutrients(nutrients: List<NutrientsData>, modifier: Modifier = Modifier) {
    VerticalStackedBarChart(nutrientsToGroupBar(nutrients), modifier)
}

@Composable
fun VerticalStackedBarChart(groupBarData: List<GroupBar>, modifier: Modifier = Modifier) {
    val legendLabels = COLOR_PALETTE.mapIndexed { index, color ->
        LegendLabel(color, LABELS[index])
    }
    val xAxisData = AxisData.Builder()
        .steps(groupBarData.count() - 1)
        .startDrawPadding(BAR_WIDTH)
        .build()
    val yAxisData = AxisData.Builder()
        .steps(Y_STEP_SIZE)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index ->
            val maxValue = groupBarData.maxOfOrNull { groupBar ->
                groupBar.barList.sumOf { it.point.y.toDouble() }.toFloat()
            } ?: 0f
            (index * (maxValue.toInt() / Y_STEP_SIZE)).toString()
        }
        .build()
    val legendsConfig = LegendsConfig(
        colorBoxSize = 8.dp,
        spaceBWLabelAndColorBox = 4.dp,
        legendLabelList = legendLabels,
        gridColumnCount = COLOR_PALETTE.size
    )
    val groupBarPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(
            barWidth = BAR_WIDTH,
            selectionHighlightData = SelectionHighlightData(
                highlightTextOffset = 5.dp,
                groupBarPopUpLabel = { x, value ->
                    "${xToName(x)}: ${value.toInt()}"
                }
            )
        ),
        barColorPaletteList = COLOR_PALETTE
    )
    val groupBarChartData = GroupBarChartData(
        barPlotData = groupBarPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        drawBar = { drawScope, _, barStyle, drawOffset, height, barIndex ->
            with(drawScope) {
                drawRect(
                    color = COLOR_PALETTE[barIndex],
                    topLeft = drawOffset,
                    size = Size(barStyle.barWidth.toPx(), height),
                    style = barStyle.barDrawStyle,
                    blendMode = barStyle.barBlendMode
                )
            }
        }
    )
    Column(
        modifier.height(CHART_HEIGHT + LEGEND_HEIGHT)
    ) {
        Legends(
            legendsConfig = legendsConfig,
            modifier = Modifier.height(LEGEND_HEIGHT)
        )
        StackedBarChart(
            modifier = Modifier.height(CHART_HEIGHT),
            groupBarChartData = groupBarChartData
        )
    }
}

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

private fun xToName(x: String): String {
    return when (x) {
        "0" -> "Белки"
        "1" -> "Углеводы"
        "2" -> "Жиры"
        "3" -> "Калории"
        else -> throw IllegalArgumentException("No such nutrient")
    }
}

@Preview(showBackground = true)
@Composable
private fun StackableNutrientsPreview() {
    MATheme {
        val nutritionData = listOf(
            NutrientsData(100f, 30f, 30f, 100f),
            NutrientsData(40f, 50f, 30f, 20f),
            NutrientsData(30f, 10f, 20f, 20f),
            NutrientsData(30f, 10f, 20f, 20f),
            NutrientsData(30f, 10f, 20f, 20f),
            NutrientsData(30f, 10f, 20f, 20f),
            NutrientsData(30f, 10f, 20f, 20f),
        )
        StackableNutrients(nutritionData)
    }
}