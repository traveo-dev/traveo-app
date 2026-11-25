package com.traveo.app.feature.trips.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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

// Accents for Activity / Food. The theme has no token for these yet.
private val ActivityPurple = Color(0xFF8B5CF6)
private val FoodOrange = Color(0xFFFF9F1C)

private data class AddEventOption(
    @DrawableRes val iconRes: Int,
    val accent: Color,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventBottomSheet(
    onDismiss: () -> Unit = {},
    onSelectTransport: () -> Unit = {},
    onSelectStay: () -> Unit = {},
    onSelectActivity: () -> Unit = {},
    onSelectFood: () -> Unit = {},
    onScanTicket: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val options = listOf(
        AddEventOption(
            iconRes = R.drawable.ic_plane,
            accent = MaterialTheme.colorScheme.primary,
            title = "Transport",
            subtitle = "Flight, train, taxi",
            onClick = onSelectTransport
        ),
        AddEventOption(
            iconRes = R.drawable.ic_bed,
            accent = SuccessGreen,
            title = "Stay",
            subtitle = "Hotel, rental, hostel",
            onClick = onSelectStay
        ),
        AddEventOption(
            iconRes = R.drawable.ic_ticket,
            accent = ActivityPurple,
            title = "Activity",
            subtitle = "Tours, tickets, spots",
            onClick = onSelectActivity
        ),
        AddEventOption(
            iconRes = R.drawable.ic_food,
            accent = FoodOrange,
            title = "Food",
            subtitle = "Restaurants, cafés",
            onClick = onSelectFood
        )
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(44.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Column {
                Text(
                    text = "Add to Day 1",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Sat, 12 September",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }

            // 2×2 options
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                options.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowItems.forEach { option ->
                            EventOptionCard(option = option, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // Scan a ticket
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(onClick = onScanTicket)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Scan a ticket or booking",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "We fill in the details for you",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "Open",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun EventOptionCard(
    option: AddEventOption,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = option.onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(option.accent.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = option.iconRes),
                contentDescription = null,
                tint = option.accent,
                modifier = Modifier.size(22.dp)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = option.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = option.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddEventBottomSheetPreview() {
    TraveoTheme {
        AddEventBottomSheet()
    }
}