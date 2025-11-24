package com.example.expensetrackerapp.ui.screens


import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.ui.viewModels.TotalScreenUiState
import com.example.expensetrackerapp.ui.viewModels.TotalScreenViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun TotalScreen(
    viewModel: TotalScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    TotalScreen2(
        state = uiState,
        onBackClick = onBackClick
    )

}

@Composable
fun TotalScreen2(
    state: TotalScreenUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ExpenseListAppBar(
                title = stringResource(R.string.statistics),
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is TotalScreenUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Cyan),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is TotalScreenUiState.Success -> {
                    TotalScreenTabbedContent(
                        totalAmount = state.totalAmount,
                        categoryTotals = state.categoryTotals
                    )
                }

                is TotalScreenUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Cyan),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = state.message)
                    }
                }
            }
        }
    }
}

@Composable
fun TotalScreenTabbedContent(
    totalAmount: Double,
    categoryTotals: Map<String, Double>
) {
    val tabTitles = listOf(stringResource(R.string.tabtitle1), stringResource(R.string.tabtitle3))
    val pagerState = rememberPagerState { tabTitles.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Blue
                )
            }) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = title) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.LightGray
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            when (pageIndex) {
                0 -> TotalExpenses(totalAmount = totalAmount)
                1 -> TotalExpensesPieChart(data = categoryTotals)
            }
        }
    }
}

@Composable
private fun TotalExpenses(
    totalAmount: Double
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = String.format(Locale.US, "Total expenses: %.2f€", totalAmount),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun TotalExpensesPieChart(
    data: Map<String, Double>
) {
    val total = data.values.sum()

    if (data.isEmpty() || total == 0.0) {
        NoDataState()
        return
    }

    val chartColors = listOf(
        Color(0xFFE57373),
        Color(color = 0xFF81C784),
        Color(color = 0xFF64B5F6),
        Color(color = 0xFFFFD54F),
        Color(0xFFBA68C8),
        Color(0xFF4DB6AC),
        Color(0xFFFF8A65)
    ).map { it.toArgb() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AndroidView(
                    factory = { context ->
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            this.description.isEnabled = false
                            this.isDrawHoleEnabled = false
                            this.legend.isEnabled = true
                            this.legend.textSize = 14F
                            this.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                            this.setEntryLabelColor(Color.Black.toArgb())
                            this.legend.isWordWrapEnabled = true
                        }
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp), update = { pieChart ->
                        updatePieChartWithData(pieChart, data, chartColors)
                    })
            }
        }
    }
}

fun updatePieChartWithData(
    chart: PieChart,
    data: Map<String, Double>,
    colors: List<Int>
) {
    val entries = ArrayList<PieEntry>()
    data.forEach { (categoryName, amount) ->
        if (amount > 0) {
            entries.add(PieEntry(amount.toFloat(), categoryName))
        }
    }

    val dataSet = PieDataSet(entries, "")
    dataSet.colors = colors
    dataSet.valueTextColor = android.graphics.Color.BLACK
    dataSet.valueTextSize = 14f
    dataSet.sliceSpace = 3f

    dataSet.setDrawValues(false)

    val pieData = PieData(dataSet)
    chart.data = pieData
    chart.animateY(1000)
    chart.invalidate()
    chart.setDrawEntryLabels(false)
}

@Composable
fun NoDataState() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Cyan
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.mesg_totalScreen),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}