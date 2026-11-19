package com.traveo.app.feature.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme
import com.traveo.app.feature.finance.presentation.FinanceScreen
import com.traveo.app.feature.home.presentation.HomeScreen
import com.traveo.app.feature.profile.presentation.ProfileScreen
import com.traveo.app.feature.trips.presentation.MyTripsScreen

private data class NavItem(
    val title: String,
    val iconRes: Int
)

private val NavItems = listOf(
    NavItem("Home", R.drawable.ic_nav_home),
    NavItem("Finance", R.drawable.ic_nav_finance),
    NavItem("My Trips", R.drawable.ic_nav_trips),
    NavItem("Profile", R.drawable.ic_nav_profile)
)

@Composable
fun MainScreen(
    onSearchClick: () -> Unit = {},
    onResortClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }

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
                3 -> ProfileScreen()
            }
        }

        // Glass Blur Backdrop Scrim & Floating Navigation Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.0f),
                            Color.White.copy(alpha = 0.6f),
                            Color.White.copy(alpha = 0.9f),
                            Color.White
                        )
                    )
                )
                .navigationBarsPadding() // Protects from Android gesture/nav bar overlap
                .padding(start = 40.dp, end = 40.dp, bottom = 36.dp, top = 16.dp)
        ) {
            FloatingBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    }
}

@Composable
fun FloatingBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = 3.dp, // Faint compact shadow
                shape = RoundedCornerShape(50.dp),
                clip = false
            )
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .padding(4.dp)
    ) {
        val totalWidth = maxWidth
        val inactiveTabWidth = 46.dp
        val activePillWidth = totalWidth - (inactiveTabWidth * (NavItems.size - 1))

        // Liquid Spring Spec for fluid morphing
        val springSpec = spring<androidx.compose.ui.unit.Dp>(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy
        )

        // Fluid Animated Horizontal Position
        val animatedPillOffset by animateDpAsState(
            targetValue = inactiveTabWidth * selectedTab,
            animationSpec = springSpec,
            label = "liquidPillOffset"
        )

        // Fluid Animated Width based on active selection
        val animatedPillWidth by animateDpAsState(
            targetValue = activePillWidth,
            animationSpec = springSpec,
            label = "liquidPillWidth"
        )

        // Sliding Active Blue Liquid Pill
        Box(
            modifier = Modifier
                .offset { IntOffset(x = animatedPillOffset.roundToPx(), y = 0) }
                .width(animatedPillWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.primary)
        )

        // Tab Items Row
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItems.forEachIndexed { index, item ->
                val isSelected = selectedTab == index
                val itemWidth = if (isSelected) activePillWidth else inactiveTabWidth

                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // No Ripple Effect
                        ) { onTabSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            tint = if (isSelected) Color.White else Color(0xFF8E8E93),
                            modifier = Modifier.size(20.dp)
                        )

                        AnimatedVisibility(
                            visible = isSelected,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Row {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
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
