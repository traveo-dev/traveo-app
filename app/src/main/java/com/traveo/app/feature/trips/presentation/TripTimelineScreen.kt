package com.traveo.app.feature.trips.presentation

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.traveo.app.core.designsystem.theme.SuccessGreen
import com.traveo.app.core.designsystem.theme.TraveoTheme

// Accent colors for the taxi / dinner events. The theme has no token for these yet.
private val TaxiPurple = Color(0xFF8B5CF6)
private val DinnerOrange = Color(0xFFFF9F1C)

private val TimelineDays = listOf(
    "Sat" to "12",
    "Sun" to "13",
    "Mon" to "14",
    "Tue" to "15",
    "Wed" to "16",
    "Thu" to "17",
    "Fri" to "18"
)

private data class BufferNote(
    @DrawableRes val iconRes: Int,
    val text: String
)

private data class TimelineEvent(
    val time: String,
    val duration: String,
    @DrawableRes val iconRes: Int,
    val accent: Color,
    val title: String,
    val subtitle: String,
    val tags: List<String>,
    val buffer: BufferNote? = null
)

@Composable
fun TripTimelineScreen(
    onBackClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {},
    onEventClick: () -> Unit = {}
) {
    var selectedDayIndex by remember { mutableIntStateOf(0) }
    var showAddEventSheet by remember { mutableStateOf(false) }

    val primary = MaterialTheme.colorScheme.primary
    val events = listOf(
        TimelineEvent(
            time = "07:30",
            duration = "30 min",
            iconRes = R.drawable.ic_car,
            accent = TaxiPurple,
            title = "Taxi to Chopin Airport",
            subtitle = "Uber Comfort · booked",
            tags = listOf("$18", "Paid by Kate"),
            buffer = BufferNote(R.drawable.ic_clock, "45 min buffer before check-in")
        ),
        TimelineEvent(
            time = "09:15",
            duration = "1h 05m",
            iconRes = R.drawable.ic_plane,
            accent = primary,
            title = "Warsaw → Kraków",
            subtitle = "LO 3823 · Gate A12 · Seat 14C",
            tags = listOf("Boarding pass", "$96"),
            buffer = BufferNote(R.drawable.ic_train, "25 min by train to the centre")
        ),
        TimelineEvent(
            time = "11:45",
            duration = "2 nights",
            iconRes = R.drawable.ic_bed,
            accent = SuccessGreen,
            title = "Hotel Stary",
            subtitle = "Check-in from 14:00 · Szczepańska 5",
            tags = listOf("Confirmed", "$198"),
            buffer = BufferNote(R.drawable.ic_clock, "Free afternoon")
        ),
        TimelineEvent(
            time = "19:00",
            duration = "2h",
            iconRes = R.drawable.ic_food,
            accent = DinnerOrange,
            title = "Dinner at Szara Gęś",
            subtitle = "Rynek Główny 17 · table for 4",
            tags = listOf("Reserved")
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp), // room for the FAB
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircleIconButton(
                        iconRes = R.drawable.ic_arrow_back,
                        contentDescription = "Back",
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )

                    Text(
                        text = "Timeline",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    CircleIconButton(
                        iconRes = R.drawable.ic_filter,
                        contentDescription = "Filter",
                        onClick = { },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                // Date strip
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(TimelineDays) { index, (day, date) ->
                        val isSelected = selectedDayIndex == index

                        Column(
                            modifier = Modifier
                                .size(width = 46.dp, height = 64.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(if (isSelected) primary else MaterialTheme.colorScheme.surface)
                                .clickable { selectedDayIndex = index },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = day,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                color = if (isSelected) Color.White.copy(alpha = 0.8f)
                                else MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = date,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 18.sp
                                ),
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

                // Day header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Day 1 · Sat, 12 September",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "4 events · $312",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                // Events
                Column {
                    events.forEach { event ->
                        TimelineEventItem(event = event, onClick = onEventClick)
                    }
                }
            }
        }

        // FAB
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 32.dp, end = 16.dp)
                .size(56.dp)
                .clip(CircleShape)
                .background(primary)
                .clickable { showAddEventSheet = true },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add event",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        // Add Event Bottom Sheet Modal
        if (showAddEventSheet) {
            AddEventBottomSheet(
                onDismiss = { showAddEventSheet = false },
                onSelectTransport = {
                    showAddEventSheet = false
                    onAddEventClick()
                },
                onSelectStay = {
                    showAddEventSheet = false
                    onAddEventClick()
                },
                onSelectActivity = {
                    showAddEventSheet = false
                    onAddEventClick()
                },
                onSelectFood = {
                    showAddEventSheet = false
                    onAddEventClick()
                },
                onScanTicket = {
                    showAddEventSheet = false
                    onAddEventClick()
                }
            )
        }
    }
}

@Composable
private fun TimelineEventItem(
    event: TimelineEvent,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Time
            Column(
                modifier = Modifier
                    .width(48.dp)
                    .padding(top = 14.dp)
            ) {
                Text(
                    text = event.time,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = event.duration,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Card
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable(onClick = onClick)
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(event.accent.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = event.iconRes),
                            contentDescription = null,
                            tint = event.accent,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = event.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.outline,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        contentDescription = "Details",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(18.dp)
                    )
                }

                if (event.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        event.tags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(36.dp))
                                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = MaterialTheme.colorScheme.outline,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        if (event.buffer != null) {
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 58.dp + 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(22.dp)
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(id = event.buffer.iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = event.buffer.text,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
private fun CircleIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TripTimelineScreenPreview() {
    TraveoTheme {
        TripTimelineScreen()
    }
}
