package com.traveo.app.feature.trips.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.SuccessGreen
import com.traveo.app.core.designsystem.theme.TraveoTheme

// Accent for the "Walk" option. The theme has no token for it yet.
private val WalkPurple = Color(0xFF8B5CF6)

private data class TravelMode(
    @DrawableRes val iconRes: Int,
    val name: String
)

private data class RouteOption(
    @DrawableRes val iconRes: Int,
    val accent: Color,
    val title: String,
    val subtitle: String,
    val duration: String,
    val price: String,
    val badge: String? = null,
    val badgeHighlighted: Boolean = false
)

private val TravelModes = listOf(
    TravelMode(R.drawable.ic_car, "Taxi"),
    TravelMode(R.drawable.ic_bus, "Transit"),
    TravelMode(R.drawable.ic_walk, "Walk"),
    TravelMode(R.drawable.ic_car, "Drive")
)

@Composable
fun DirectionsScreen(
    onBackClick: () -> Unit = {},
    onSaveToTripClick: () -> Unit = {}
) {
    var selectedModeIndex by remember { mutableIntStateOf(0) }
    var selectedRouteIndex by remember { mutableIntStateOf(0) }

    val primary = MaterialTheme.colorScheme.primary
    val routes = listOf(
        RouteOption(
            iconRes = R.drawable.ic_car,
            accent = primary,
            title = "Uber Comfort",
            subtitle = "Door to door · light traffic",
            duration = "26 min",
            price = "$18",
            badge = "Fastest",
            badgeHighlighted = true
        ),
        RouteOption(
            iconRes = R.drawable.ic_bus,
            accent = SuccessGreen,
            title = "Bus 175 → Train SKM",
            subtitle = "2 changes · every 12 min",
            duration = "41 min",
            price = "$3.20",
            badge = "Cheapest"
        ),
        RouteOption(
            iconRes = R.drawable.ic_walk,
            accent = WalkPurple,
            title = "Walk → Train SKM",
            subtitle = "8 min walk to the station",
            duration = "38 min",
            price = "$2.40"
        )
    )

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
                        text = "How to get there",
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

                // Origin / destination
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Hotel Stary · Szczepańska 5",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1
                            )
                        }

                        // Dotted connector
                        Column(
                            modifier = Modifier
                                .width(20.dp)
                                .padding(vertical = 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            repeat(3) {
                                Box(
                                    modifier = Modifier
                                        .size(2.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_location_outline),
                                contentDescription = null,
                                tint = primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Chopin Airport, Terminal 2",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_swap),
                            contentDescription = "Swap",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Travel modes
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(TravelModes) { index, mode ->
                        val isSelected = selectedModeIndex == index
                        val contentColor =
                            if (isSelected) Color.White else MaterialTheme.colorScheme.outline

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) primary else MaterialTheme.colorScheme.surface)
                                .clickable { selectedModeIndex = index }
                                .padding(horizontal = 14.dp, vertical = 9.dp),
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

                // Map preview
                DirectionsMapPreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(146.dp)
                        .clip(RoundedCornerShape(24.dp))
                )

                Text(
                    text = "3 ways to get there",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )

                // Options
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    routes.forEachIndexed { index, option ->
                        RouteOptionCard(
                            option = option,
                            isSelected = selectedRouteIndex == index,
                            onClick = { selectedRouteIndex = index }
                        )
                    }
                }
            }

            // Pinned actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_map),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Open in Maps",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(50))
                        .background(primary)
                        .clickable(onClick = onSaveToTripClick),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save to trip",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun RouteOptionCard(
    option: RouteOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(24.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .then(if (isSelected) Modifier.border(1.5.dp, primary, shape) else Modifier)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = option.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )

                if (option.badge != null) {
                    Spacer(modifier = Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (option.badgeHighlighted) primary
                                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = option.badge,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = if (option.badgeHighlighted) Color.White
                            else MaterialTheme.colorScheme.outline,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = option.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = option.duration,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 15.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = option.price,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

/** Stylised map thumbnail with the elbow route, built only from theme colors. */
@Composable
private fun DirectionsMapPreview(modifier: Modifier = Modifier) {
    val road = MaterialTheme.colorScheme.surface
    val background = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)
    val block = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)
    val park = SuccessGreen.copy(alpha = 0.2f)
    val primary = MaterialTheme.colorScheme.primary
    val river = primary.copy(alpha = 0.2f)
    val outline = MaterialTheme.colorScheme.outline

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val corner = CornerRadius(16.dp.toPx())

        drawRect(color = background)

        fun fillBlock(color: Color, l: Float, t: Float, r: Float, b: Float) {
            drawRoundRect(
                color = color,
                topLeft = Offset(w * l, h * t),
                size = Size(w * (r - l), h * (b - t)),
                cornerRadius = corner
            )
        }

        fillBlock(park, 0.611f, 0.068f, 0.975f, 0.391f)
        fillBlock(block, 0.039f, 0.093f, 0.336f, 0.422f)
        fillBlock(block, 0.620f, 0.565f, 0.961f, 0.907f)

        // River
        val riverPath = Path().apply {
            moveTo(0f, h * 0.72f)
            cubicTo(w * 0.25f, h * 0.58f, w * 0.35f, h * 0.92f, w * 0.60f, h * 0.78f)
            cubicTo(w * 0.80f, h * 0.66f, w * 0.90f, h * 0.70f, w, h * 0.60f)
        }
        drawPath(path = riverPath, color = river, style = Stroke(width = 24.dp.toPx()))

        // Roads
        drawRect(road, Offset(w * 0.375f, 0f), Size(14.dp.toPx(), h))
        drawRect(road, Offset(w * 0.755f, 0f), Size(8.dp.toPx(), h))
        drawRect(road, Offset(0f, h * 0.143f), Size(w, 10.dp.toPx()))
        drawRect(road, Offset(0f, h * 0.466f), Size(w, 13.dp.toPx()))

        // Route
        val start = Offset(w * 0.155f, h * 0.199f)
        val end = Offset(w * 0.841f, h * 0.745f)
        val route = Path().apply {
            moveTo(start.x, start.y)
            lineTo(w * 0.400f, h * 0.199f)
            lineTo(w * 0.400f, h * 0.478f)
            lineTo(w * 0.841f, h * 0.478f)
            lineTo(end.x, end.y)
        }
        drawPath(
            path = route,
            color = primary,
            style = Stroke(
                width = 6.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.cornerPathEffect(14.dp.toPx())
            )
        )

        // Origin and destination
        drawCircle(color = road, radius = 9.dp.toPx(), center = start)
        drawCircle(color = outline, radius = 9.dp.toPx(), center = start, style = Stroke(width = 3.dp.toPx()))
        drawCircle(color = road, radius = 10.dp.toPx(), center = end)
        drawCircle(color = primary, radius = 7.dp.toPx(), center = end)
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
fun DirectionsScreenPreview() {
    TraveoTheme {
        DirectionsScreen()
    }
}