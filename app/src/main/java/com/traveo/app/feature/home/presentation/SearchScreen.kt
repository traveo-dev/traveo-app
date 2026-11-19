package com.traveo.app.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.component.CategoryChips
import com.traveo.app.core.designsystem.component.CompactResortCard
import com.traveo.app.core.designsystem.component.ResortItem
import com.traveo.app.core.designsystem.component.SampleResorts
import com.traveo.app.core.designsystem.component.TraveoSearchBar
import com.traveo.app.core.designsystem.theme.TraveoTheme

data class RecentSearch(
    val title: String,
    val subtitle: String
)

private val DefaultRecentSearches = listOf(
    RecentSearch("The Mulia", "Villas · Sep 4 — 7 · 2 guests"),
    RecentSearch("Kayumanis Jimbaran Estate", "Cottage · Sep 4 — 8 · 2 guests")
)

private val SearchResults = listOf(SampleResorts[1], SampleResorts[2])
private val TrendingResorts = listOf(SampleResorts[0], SampleResorts[1])

@Composable
fun SearchScreen(
    onBackClick: () -> Unit = {},
    onResortClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val recentSearches = remember { mutableStateListOf(*DefaultRecentSearches.toTypedArray()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(bottom = 100.dp),
        ) {
            // Search Bar Header
            TraveoSearchBar(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onLocationClick = onBackClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Category Chips Row
            CategoryChips(
                selectedIndex = selectedCategoryIndex,
                onSelected = { selectedCategoryIndex = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Recent Searches Section
            if (recentSearches.isNotEmpty()) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader(
                        title = "Recent",
                        actionText = "Clear all",
                        onActionClick = { recentSearches.clear() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    recentSearches.forEachIndexed { index, item ->
                        val isLast = index == recentSearches.lastIndex

                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = onResortClick)
                                    .padding(bottom = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_home),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = item.subtitle,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_up_right),
                                    contentDescription = "Open",
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            if (!isLast) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color(0x0F000000))
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // Search Results
            ResortRow(resorts = SearchResults, onResortClick = onResortClick)

            Spacer(modifier = Modifier.height(20.dp))

            // Trending in Bali Section
            SectionHeader(
                title = "Trending in Bali",
                actionText = "See all",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ResortRow(resorts = TrendingResorts, onResortClick = onResortClick)
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String,
    modifier: Modifier = Modifier,
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = actionText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline,
            modifier = if (onActionClick != null) Modifier.clickable(onClick = onActionClick)
            else Modifier
        )
    }
}

@Composable
private fun ResortRow(
    resorts: List<ResortItem>,
    onResortClick: () -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(resorts) { resort ->
            CompactResortCard(
                resort = resort,
                onClick = onResortClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    TraveoTheme {
        SearchScreen()
    }
}
