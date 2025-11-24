package com.example.expensetrackerapp.ui.screens


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetrackerapp.R

@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit,

    ) {

    AppBar { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.Cyan
        )
        {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(categories) { category ->
                    CategoryItem(
                        category = category,
                        onClick = { onCategoryClick(category.navigationRoute) },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryMenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Blue
        ),
        modifier = Modifier
            .clickable {
                onClick()
            }
            .size(150.dp)
            .padding(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryIcon(
                category.iconResId,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoryTitle(category.title)
        }
    }

}

@Composable
fun CategoryIcon(
    @DrawableRes exIcon: Int,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(exIcon),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = modifier
    )
}

@Composable
fun CategoryTitle(
    @StringRes category: Int
) {
    Text(
        text = stringResource(category),
        fontSize = 20.sp,
        color = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_bar_text),
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue
                )
            )
        },
        content = content
    )
}