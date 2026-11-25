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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private data class TransportMode(
    @DrawableRes val iconRes: Int,
    val name: String
)

private val TransportModes = listOf(
    TransportMode(R.drawable.ic_plane, "Flight"),
    TransportMode(R.drawable.ic_train, "Train"),
    TransportMode(R.drawable.ic_bus, "Bus"),
    TransportMode(R.drawable.ic_car, "Taxi"),
    TransportMode(R.drawable.ic_ferry, "Ferry")
)

@Composable
fun AddTransportScreen(
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    var selectedModeIndex by remember { mutableIntStateOf(0) }
    var flightNumber by remember { mutableStateOf("LO 3823") }

    val primary = MaterialTheme.colorScheme.primary

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
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
                        text = "Add transport",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    CircleIconButton(
                        iconRes = R.drawable.ic_close,
                        contentDescription = "Close",
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                // Transport modes
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(TransportModes) { index, mode ->
                        val isSelected = selectedModeIndex == index
                        val contentColor =
                            if (isSelected) Color.White else MaterialTheme.colorScheme.outline

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) primary else MaterialTheme.colorScheme.surface)
                                .clickable { selectedModeIndex = index }
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = mode.iconRes),
                                contentDescription = null,
                                tint = contentColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = mode.name,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = contentColor,
                                maxLines = 1
                            )
                        }
                    }
                }

                // Auto-fill helper
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(primary.copy(alpha = 0.1f))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bolt),
                            contentDescription = null,
                            tint = primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Enter the flight number — we fill in times, terminals and gates",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            ),
                            color = primary
                        )
                    }

                    BasicTextField(
                        value = flightNumber,
                        onValueChange = { flightNumber = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(36.dp))
                            .background(MaterialTheme.colorScheme.surface),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        cursorBrush = SolidColor(primary),
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 18.dp, end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (flightNumber.isEmpty()) {
                                        Text(
                                            text = "Flight number",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                    innerTextField()
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(primary)
                                        .clickable { }
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        text = "Find",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    )
                }

                // Details
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TransportField(
                            label = "From",
                            value = "Warsaw · WAW",
                            leadingIconRes = R.drawable.ic_location_outline,
                            modifier = Modifier.weight(1f)
                        )
                        TransportField(
                            label = "To",
                            value = "Kraków · KRK",
                            leadingIconRes = R.drawable.ic_location_outline,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TransportField(
                            label = "Departure",
                            value = "12 Sep · 09:15",
                            hasDropdown = true,
                            modifier = Modifier.weight(1f)
                        )
                        TransportField(
                            label = "Arrival",
                            value = "12 Sep · 10:20",
                            hasDropdown = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TransportField(
                            label = "Seat",
                            value = "14C",
                            modifier = Modifier.weight(1f)
                        )
                        TransportField(
                            label = "Class",
                            value = "Economy",
                            hasDropdown = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Price + split
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_money),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Price",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "$96.00",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Split between 4 travellers",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.weight(1f)
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                            listOf(
                                R.drawable.img_profile_logo,
                                R.drawable.img_profile_logo,
                                R.drawable.img_profile_logo
                            ).forEach { avatarRes ->
                                Image(
                                    painter = painterResource(id = avatarRes),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                // Attach boarding pass
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_attach),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Attach boarding pass",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Goes straight to your Travel wallet",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        contentDescription = "Attach",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Pinned button
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(primary)
                    .clickable(onClick = onAddClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add to Day 1",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun TransportField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int? = null,
    hasDropdown: Boolean = false
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIconRes != null) {
                Icon(
                    painter = painterResource(id = leadingIconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )

            if (hasDropdown) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(90f)
                )
            }
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
fun AddTransportScreenPreview() {
    TraveoTheme {
        AddTransportScreen()
    }
}