package com.traveo.app.feature.trips.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.SuccessGreen
import com.traveo.app.core.designsystem.theme.TraveoTheme

private val MapFilterDays = listOf("All days", "Day 1", "Day 2", "Day 3", "Day 4")

/** Marker positions as fractions of the map area. */
private data class MapStop(val number: Int, val x: Float, val y: Float)

private val RouteStops = listOf(
    MapStop(1, 0.249f, 0.271f),
    MapStop(2, 0.549f, 0.377f),
    MapStop(3, 0.411f, 0.588f),
    MapStop(4, 0.721f, 0.660f)
)

private data class StopCardItem(
    val number: Int,
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int
)

private val StopCards = listOf(
    StopCardItem(2, "Warsaw → Kraków", "09:15 · LO 3823", R.drawable.resort_kayumanis),
    StopCardItem(3, "Hotel Stary", "11:45 · Szczepańska 5", R.drawable.resort_the_mulia),
    StopCardItem(4, "Dinner at Szara Gęś", "19:00 · Rynek Główny 17", R.drawable.resort_alila)
)

@Composable
fun TripMapScreen(
    onBackClick: () -> Unit = {},
    onBuildRouteClick: () -> Unit = {}
) {
    var selectedDayIndex by remember { mutableIntStateOf(1) }
    var activeStop by remember { mutableIntStateOf(2) }
    var searchQuery by remember { mutableStateOf("") }

    val primary = MaterialTheme.colorScheme.primary

    Box(modifier = Modifier.fillMaxSize()) {
        // Map placeholder: swap for a real map later
        MapBackdrop(modifier = Modifier.fillMaxSize())

        // Route markers
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            RouteStops.forEach { stop ->
                val isActive = stop.number == activeStop
                val markerSize = if (isActive) 40.dp else 36.dp

                Box(
                    modifier = Modifier
                        .offset(
                            x = maxWidth * stop.x - markerSize / 2,
                            y = maxHeight * stop.y - markerSize / 2
                        )
                        .size(markerSize)
                        .clip(CircleShape)
                        .background(if (isActive) primary else MaterialTheme.colorScheme.surface)
                        .border(
                            width = if (isActive) 3.dp else 2.dp,
                            color = if (isActive) MaterialTheme.colorScheme.surface else primary,
                            shape = CircleShape
                        )
                        .clickable { activeStop = stop.number },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stop.number.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isActive) Color.White else primary
                    )
                }
            }
        }

        // Search + day filters
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MapCircleButton(
                    iconRes = R.drawable.ic_arrow_back,
                    contentDescription = "Back",
                    onClick = onBackClick
                )

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surface),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    cursorBrush = SolidColor(primary),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Search on the map",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.outline,
                                        maxLines = 1
                                    )
                                }
                                innerTextField()
                            }
                        }
                    }
                )

                MapCircleButton(
                    iconRes = R.drawable.ic_layers,
                    contentDescription = "Map layers",
                    onClick = { }
                )
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(MapFilterDays) { index, title ->
                    val isSelected = selectedDayIndex == index

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) primary else MaterialTheme.colorScheme.surface)
                            .clickable { selectedDayIndex = index }
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
        }

        // Locate-me button + bottom sheet
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 86.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                MapCircleButton(
                    iconRes = R.drawable.ic_my_location,
                    contentDescription = "My location",
                    onClick = { }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp)
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(44.dp)
                        .height(5.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f))
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Day 1 · 4 stops",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "12.4 km · 2h 40m on the move",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(primary)
                            .clickable { }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "Optimise",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(StopCards) { stop ->
                        StopCard(stop = stop)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(primary)
                        .clickable(onClick = onBuildRouteClick),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_route),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Build route between stops",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun StopCard(stop: StopCardItem) {
    Row(
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = stop.imageRes),
            contentDescription = stop.title,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stop.number.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = stop.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = stop.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun MapCircleButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
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

/** Stylised street map built only from theme colors; the route line is drawn on top. */
@Composable
private fun MapBackdrop(modifier: Modifier = Modifier) {
    val road = MaterialTheme.colorScheme.surface
    val background = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)
    val block = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)
    val park = SuccessGreen.copy(alpha = 0.2f)
    val route = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val corner = CornerRadius(24.dp.toPx())

        drawRect(color = background)

        fun fillBlock(color: Color, l: Float, t: Float, r: Float, b: Float) {
            drawRoundRect(
                color = color,
                topLeft = Offset(w * l, h * t),
                size = Size(w * (r - l), h * (b - t)),
                cornerRadius = corner
            )
        }

        // Park + building blocks
        fillBlock(park, 0.564f, 0.067f, 0.962f, 0.277f)
        fillBlock(block, 0.036f, 0.031f, 0.251f, 0.143f)
        fillBlock(block, 0.036f, 0.204f, 0.330f, 0.373f)
        fillBlock(block, 0.064f, 0.474f, 0.406f, 0.631f)
        fillBlock(block, 0.617f, 0.474f, 0.968f, 0.631f)
        fillBlock(block, 0.340f, 0.665f, 0.613f, 0.725f)

        // Roads
        fun vertical(xFraction: Float, widthDp: Float) =
            drawRect(color = road, topLeft = Offset(w * xFraction, 0f), size = Size(widthDp.dp.toPx(), h))

        fun horizontal(yFraction: Float, heightDp: Float) =
            drawRect(color = road, topLeft = Offset(0f, h * yFraction), size = Size(w, heightDp.dp.toPx()))

        vertical(0.251f, 20f)
        vertical(0.794f, 10f)
        horizontal(0.419f, 15f)
        horizontal(0.273f, 10f)
        horizontal(0.657f, 8f)

        // Diagonal avenue
        drawLine(
            color = road,
            start = Offset(0f, h * 0.176f),
            end = Offset(w, h * 0.54f),
            strokeWidth = 14.dp.toPx()
        )

        // Route
        val path = Path().apply {
            RouteStops.forEachIndexed { index, stop ->
                if (index == 0) moveTo(w * stop.x, h * stop.y) else lineTo(w * stop.x, h * stop.y)
            }
        }
        drawPath(
            path = path,
            color = route,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TripMapScreenPreview() {
    TraveoTheme {
        TripMapScreen()
    }
}