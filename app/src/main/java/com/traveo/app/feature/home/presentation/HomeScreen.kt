package com.traveo.app.feature.home.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.component.CategoryChips
import com.traveo.app.core.designsystem.component.FeaturedResortCard
import com.traveo.app.core.designsystem.component.SampleResorts
import com.traveo.app.core.designsystem.component.TraveoSearchBar
import com.traveo.app.core.designsystem.theme.TraveoTheme

data class CategoryItem(
    val title: String,
    @DrawableRes val iconRes: Int
)

val categoryList = listOf(
    CategoryItem("Stays", R.drawable.ic_tab_stays),
    CategoryItem("Flights", R.drawable.ic_tab_flights),
    CategoryItem("Rentals", R.drawable.ic_tab_rentals),
    CategoryItem("Tours", R.drawable.ic_tab_tours)
)

@Composable
fun HomeScreen(
    onNotificationClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onResortClick: () -> Unit = {}
) {
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(bottom = 150.dp)
        ) {
            // Top Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_profile_logo),
                        contentDescription = "Profile Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Traveo Logo",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable(onClick = onNotificationClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Greeting & Title
            Column(
                modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Welcome in TraveO",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ready To Find\nYour Perfect Next Trip?",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Search Bar Capsule
            TraveoSearchBar(
                modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                onClick = onSearchClick
            )

            // "Explore nearby" Title & Category Chips
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Explore ",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "nearby",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                CategoryChips(
                    selectedIndex = selectedCategoryIndex,
                    onSelected = { selectedCategoryIndex = it }
                )
            }

            // Featured Resort Cards
            FeaturedResortCard(
                resort = SampleResorts[0],
                onClick = onResortClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeaturedResortCard(
                resort = SampleResorts[1],
                onClick = onResortClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    TraveoTheme {
        HomeScreen()
    }
}
