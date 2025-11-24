package com.example.expensetrackerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.domain.model.ExpenseItem
import com.example.expensetrackerapp.ui.viewModels.ExpenseListViewModel
import com.example.expensetrackerapp.ui.viewModels.ExpensesListUiState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel = hiltViewModel(), //->finds the right view model
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val categoryName = viewModel.categoryName

    val amountInput by viewModel.amountInput.collectAsState()
    val descriptionInput by viewModel.descriptionInput.collectAsState()

    ExpenseListScreen2(
        state = uiState,
        categoryName = categoryName,
        onBackClick = onBackClick,
        amountInput = amountInput,
        descriptionInput = descriptionInput,
        onAmountChanged = viewModel::onAmountChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onSaveClick = viewModel::onSaveClick,
        onDeleteClick = viewModel::onDeleteClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen2(
    state: ExpensesListUiState,
    categoryName: String,
    onBackClick: () -> Unit,
    amountInput: String,
    descriptionInput: String,
    onAmountChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: (ExpenseItem) -> Unit
) {
    Scaffold(
        topBar = {
            ExpenseListAppBar(
                title = categoryName,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
        {
            when (state) {
                is ExpensesListUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Cyan),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ExpensesListUiState.Success -> {
                    ExpenseListTabbedContent(
                        expenses = state.expenses,
                        amountInput = amountInput,
                        descriptionInput = descriptionInput,
                        onAmountChanged = onAmountChanged,
                        onDescriptionChanged = onDescriptionChanged,
                        onSaveClick = onSaveClick,
                        onDeleteClick = onDeleteClick
                    )
                }

                is ExpensesListUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Cyan),
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
fun ExpenseListTabbedContent(
    expenses: List<ExpenseItem>,
    amountInput: String,
    descriptionInput: String,
    onAmountChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: (ExpenseItem) -> Unit
) {

    val tabTitles = listOf(stringResource(R.string.tabtitle1), stringResource(R.string.tabtitle2))
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
                0 -> AddExpenseFrom(
                    amountInput = amountInput,
                    descriptionInput = descriptionInput,
                    onAmountChanged = onAmountChanged,
                    onDescriptionChanged = onDescriptionChanged,
                    onSaveClick = onSaveClick
                )

                1 -> ExpenseListSuccessContent(
                    expenses = expenses,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}

@Composable
fun AddExpenseFrom(
    amountInput: String,
    descriptionInput: String,
    onAmountChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Cyan
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.add_new_expense),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .heightIn(max = 150.dp),
                value = amountInput,
                onValueChange = onAmountChanged,
                label = { Text(stringResource(R.string.amount)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .heightIn(max = 150.dp),
                value = descriptionInput,
                onValueChange = onDescriptionChanged,
                label = { Text(stringResource(R.string.description)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSaveClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun ExpenseListSuccessContent(
    expenses: List<ExpenseItem>,
    onDeleteClick: (ExpenseItem) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Cyan
    ) {

        if (expenses.isEmpty()) {
            NoExpensesState()
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(max = 400.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(expenses) { expense ->
                    ExpenseItemCard(
                        expense = expense,
                        onDelete = { onDeleteClick(expense) }
                    )
                }
            }
        }
    }
}

@Composable
fun NoExpensesState() {
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
            text = stringResource(R.string.message),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
fun ExpenseItemCard(
    expense: ExpenseItem,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    expense.description,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(formatDate(expense.date))
            }
            Text(
                text = String.format(Locale.US, "%.2f€", expense.amount),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }

}

private fun formatDate(timeStamp: Long): String {
    val date = Date(timeStamp)
    return SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Blue
        ),
        modifier = modifier,
        navigationIcon = {
            IconButton(onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )

}