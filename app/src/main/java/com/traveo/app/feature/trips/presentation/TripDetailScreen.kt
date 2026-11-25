package com.traveo.app.feature.trips.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private val HeroHeight = 300.dp
private val SheetOverlap = 40.dp

private val TripTabs = listOf("Overview", "Timeline", "Map", "Budget")

private data class OverviewWidget(
    @DrawableRes val iconRes: Int,
    val title: String,
    val value: String
)

private val OverviewWidgets = listOf(
    OverviewWidget(R.drawable.ic_weather, "Weather in Kraków", "+21°C · Sunny"),
    OverviewWidget(R.drawable.ic_wallet, "Budget left", "$760 of $2 000"),
    OverviewWidget(R.drawable.ic_document, "Documents", "6 tickets & files"),
    OverviewWidget(R.drawable.ic_checklist, "Packing list", "12 of 18 done")
)

@Composable
fun TripDetailScreen(
    onBackClick: () -> Unit = {},
    onMembersClick: () -> Unit = {},
    onAddExpenseClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {},
    onEventClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> TripOverviewContent(
                    onBackClick = onBackClick,
                    onMembersClick = onMembersClick,
                    onTabSelected = { selectedTab = it },
                    onEventClick = onEventClick
                )
                1 -> TripTimelineScreen(
                    onBackClick = { selectedTab = 0 },
                    onAddEventClick = onAddEventClick,
                    onEventClick = onEventClick
                )
                2 -> TripMapScreen(
                    onBackClick = { selectedTab = 0 },
                    onBuildRouteClick = onEventClick
                )
                3 -> TripBudgetScreen(
                    onBackClick = { selectedTab = 0 },
                    onAddExpenseClick = onAddExpenseClick
                )
            }
        }
    }
}

@Composable
private fun TripOverviewContent(
    onBackClick: () -> Unit,
    onMembersClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onEventClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HeroHeight)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.resort_the_mulia),
                    contentDescription = "Poland Road Trip",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0.5f to Color.Transparent,
                                1f to Color.Black.copy(alpha = 0.45f)
                            )
                        )
                )

                // Top buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeroIconButton(
                        iconRes = R.drawable.ic_arrow_back,
                        contentDescription = "Back",
                        onClick = onBackClick
                    )
                    HeroIconButton(
                        iconRes = R.drawable.ic_more,
                        contentDescription = "More",
                        onClick = { }
                    )
                }

                // Location, title, chips
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, end = 16.dp, bottom = SheetOverlap + 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_outline),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Kraków, Poland",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Poland Road Trip",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W500
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        HeroPill(text = "12–19 Sep")
                        Spacer(modifier = Modifier.width(6.dp))
                        HeroPill(text = "3 cities")
                        Spacer(modifier = Modifier.width(6.dp))

                        Row(
                            modifier = Modifier.clickable(onClick = onMembersClick),
                            horizontalArrangement = Arrangement.spacedBy((-8).dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MemberAvatar(avatarRes = R.drawable.img_profile_logo)
                            MemberAvatar(avatarRes = R.drawable.img_profile_logo)
                            MemberAvatar(avatarRes = R.drawable.img_profile_logo)

                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                                    .border(2.dp, Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+1",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Sheet
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = HeroHeight - SheetOverlap),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Tabs
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TripTabs.forEachIndexed { index, title ->
                            val isSelected = index == 0

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(if (isSelected) primary else MaterialTheme.colorScheme.surface)
                                    .clickable { onTabSelected(index) }
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.outline,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    NextFlightCard(onClick = onEventClick)

                    // Widgets
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OverviewWidgets.chunked(2).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowItems.forEach { widget ->
                                    WidgetCard(widget = widget, modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NextFlightCard(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plane),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Warsaw → Kraków",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Next up · flight LO 3823",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "3d 4h",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        lineHeight = 1.24.em,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "To departure",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FlightTile(label = "Departure", value = "09:15 · T2", modifier = Modifier.weight(1f))
            FlightTile(label = "Gate", value = "A12", modifier = Modifier.weight(1f))
            FlightTile(label = "In the air", value = "1h 05m", modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_train),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Then: train to Zakopane · 2h 10m after landing",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun FlightTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

@Composable
private fun WidgetCard(
    widget: OverviewWidget,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = widget.iconRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = widget.title,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.outline,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = widget.value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
    }
}

@Composable
private fun HeroIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.25f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun HeroPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(36.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color.White,
            maxLines = 1
        )
    }
}

@Composable
private fun MemberAvatar(@DrawableRes avatarRes: Int) {
    Image(
        painter = painterResource(id = avatarRes),
        contentDescription = null,
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TripDetailScreenPreview() {
    TraveoTheme {
        TripDetailScreen()
    }
}