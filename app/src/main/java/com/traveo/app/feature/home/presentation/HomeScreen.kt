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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.components.CategoryChips
import com.traveo.app.core.designsystem.components.FeaturedResortCard
import com.traveo.app.core.designsystem.components.SampleResorts
import com.traveo.app.core.designsystem.components.TraveoSearchBar
import com.traveo.app.core.designsystem.theme.TraveoTheme

private val CategoryList = listOf("Stays", "Flights", "Rentals", "Tours")

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
    var selectedNavTab by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
                    .padding(bottom = 90.dp),
//                verticalArrangement = Arrangement.spacedBy(20.dp)
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

                // Featured Resort Card
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

        // 6. Floating Bottom Navigation Bar
        FloatingBottomNavigation(
            selectedTab = selectedNavTab,
            onTabSelected = { selectedNavTab = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
fun FloatingBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .height(64.dp),
        shape = RoundedCornerShape(50),
        color = Color.White,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tab 0: Home (Active Pill)
            if (selectedTab == 0) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = "Home",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            } else {
                IconButton(onClick = { onTabSelected(0) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Tab 1: Wallet
            IconButton(onClick = { onTabSelected(1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wallet),
                    contentDescription = "Wallet",
                    tint = if (selectedTab == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Tab 2: Booking
            IconButton(onClick = { onTabSelected(2) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_booking),
                    contentDescription = "Booking",
                    tint = if (selectedTab == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Tab 3: Profile
            IconButton(onClick = { onTabSelected(3) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile",
                    tint = if (selectedTab == 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(22.dp)
                )
            }
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