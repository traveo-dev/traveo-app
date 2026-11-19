package com.traveo.app.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.traveo.app.R
import com.traveo.app.core.designsystem.component.FloatingNavBar
import com.traveo.app.core.designsystem.component.floatingNavItem
import com.traveo.app.core.designsystem.theme.TraveoTheme
import com.traveo.app.feature.finance.presentation.FinanceScreen
import com.traveo.app.feature.home.presentation.HomeScreen
import com.traveo.app.feature.profile.presentation.ProfileScreen
import com.traveo.app.feature.trips.presentation.MyTripsScreen

private val MainNavItems = listOf(
    floatingNavItem("Home", R.drawable.ic_nav_home, iconSize = 24.dp),
    floatingNavItem("Finance", R.drawable.ic_nav_finance, iconSize = 24.dp),
    floatingNavItem("My Trips", R.drawable.ic_nav_trips, iconSize = 24.dp),
    floatingNavItem("Profile", R.drawable.ic_nav_profile, iconSize = 24.dp)
)

@Composable
fun MainScreen(
    onSearchClick: () -> Unit = {},
    onResortClick: () -> Unit = {},
    onProfileSearchClick: () -> Unit = {},
    onProfileUpgradeClick: () -> Unit = {},
    onProfileSettingClick: (String) -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Active Screen Content
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> HomeScreen(
                    onSearchClick = onSearchClick,
                    onResortClick = onResortClick
                )
                1 -> FinanceScreen()
                2 -> MyTripsScreen()
                3 -> ProfileScreen(
                    onSearchClick = onProfileSearchClick,
                    onUpgradeClick = onProfileUpgradeClick,
                    onSettingClick = onProfileSettingClick
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White.copy(alpha = 0.85f),
                            Color.White
                        )
                    )
                )
                .navigationBarsPadding()
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 16.dp)
        ) {
            FloatingNavBar(
                items = MainNavItems,
                selectedIndex = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    TraveoTheme {
        MainScreen()
    }
}
