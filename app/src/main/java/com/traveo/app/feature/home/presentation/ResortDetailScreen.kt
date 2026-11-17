package com.traveo.app.feature.home.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.components.SectionHeader
import com.traveo.app.core.designsystem.theme.TraveoTheme
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity

private val HeroHeight = 400.dp
private val SheetOverlap = 40.dp
private val PriceColor = Color(0xFFE8FE78)

private val TopBarHeight = 74.dp // 50dp button + 12dp padding top and bottom

private data class IconLabel(
    @DrawableRes val iconRes: Int,
    val text: String
)

private val AmenityBadges = listOf(
    IconLabel(R.drawable.ic_star_half, "4,5"),
    IconLabel(R.drawable.ic_bath, "2 Bath"),
    IconLabel(R.drawable.ic_bed, "3 Bedrooms"),
    IconLabel(R.drawable.ic_guests, "4 Guest")
)

private val Facilities = listOf(
    IconLabel(R.drawable.ic_wifi, "Free Wi-Fi"),
    IconLabel(R.drawable.ic_tv, "65\" HDTV"),
    IconLabel(R.drawable.ic_washing_machine, "Washing Machine"),
    IconLabel(R.drawable.ic_bath, "Private Jacuzzi")
)

private val GalleryImages = listOf(
    R.drawable.resort_kayumanis,
    R.drawable.resort_the_mulia,
    R.drawable.resort_alila
)

private const val ResortDescription =
    "This secluded eco-resort is tucked away in the very heart of Ubud’s lush tropical " +
            "rainforests, far from the roar of motorbikes on the main streets. There is no typical " +
            "city noise here—only the singing of tropical birds and the rustle of palm leaves. " +
            "Each villa has its own private pool, open-air living area and garden, so you can " +
            "unwind in total privacy while staying minutes away from Ubud’s cafés, rice terraces " +
            "and art markets."

@Composable
fun ResortDetailScreen(
    onBackClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onBookClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    // Scroll distance until the sheet reaches the top bar
    val statusBarPx = WindowInsets.statusBars.getTop(density)
    val collapseRangePx = with(density) {
        (HeroHeight - SheetOverlap - TopBarHeight).toPx()
    } - statusBarPx

    // 0f = expanded, 1f = sheet is at the top
    val progress by remember {
        derivedStateOf { (scrollState.value / collapseRangePx).coerceIn(0f, 1f) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. Hero (behind everything, parallax)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HeroHeight)
                .graphicsLayer { translationY = -scrollState.value * 0.5f }
        ) {
            Image(
                painter = painterResource(id = R.drawable.resort_kayumanis),
                contentDescription = "Kayumanis Jimbaran Estate",
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

            // Location, title, price, amenities
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .graphicsLayer {
                        alpha = (1f - scrollState.value / (collapseRangePx * 0.6f))
                            .coerceIn(0f, 1f)
                    }
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
                        text = "Banjar Baung, Ubud",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_up_right),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Kayumanis\nJimbaran Estate",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            lineHeight = 28.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = Color.White
                    )

                    Row {
                        Text(
                            text = "$340",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W400
                            ),
                            color = PriceColor,
                            modifier = Modifier.alignByBaseline()
                        )
                        Text(
                            text = " / night",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PriceColor,
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    AmenityBadges.forEach { AmenityBadge(it) }
                }
            }
        }

        // White sheet
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(HeroHeight - SheetOverlap))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(top = 16.dp, bottom = 140.dp)) {
                    // Description
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        SectionHeader(title = "Description")
                        Spacer(modifier = Modifier.height(24.dp))
                        ExpandableText(text = ResortDescription)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    FacilitiesGrid(
                        facilities = Facilities,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    SectionHeader(
                        title = "Gallery",
                        actionText = "See all",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(GalleryImages) { imageRes ->
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "Gallery image",
                                modifier = Modifier
                                    .size(width = 164.dp, height = 170.dp)
                                    .clip(RoundedCornerShape(24.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // TEMP: forces the page to be long enough to see the effect. Remove later.
                    Spacer(modifier = Modifier.height(500.dp))
                }
            }
        }

        // Top bar (pinned)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = progress))
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            lerp(Color.White.copy(alpha = 0.25f), Color(0xFFF3F3F3), progress)
                        )
                        .clickable(onClick = onBackClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = lerp(Color.White, MaterialTheme.colorScheme.onBackground, progress),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(lerp(Color.White, Color(0xFFF3F3F3), progress))
                        .clickable { isFavorite = !isFavorite },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFFF3E3E) else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = "Kayumanis Jimbaran Estate",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 62.dp)
                    .graphicsLayer { alpha = progress }
            )
        }

        // Floating action bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0f), Color.White)
                    )
                )
                .navigationBarsPadding()
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 24.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(36.dp),
                color = Color(0xCCFFFFFF)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActionButton(
                        text = "Call the hotel",
                        iconRes = R.drawable.ic_phone_call,
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.outline,
                        onClick = onCallClick,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        text = "Book Now",
                        iconRes = R.drawable.ic_bag_check,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        onClick = onBookClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AmenityBadge(item: IconLabel) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color.White,
            maxLines = 1
        )
    }
}

@Composable
private fun FacilitiesGrid(
    facilities: List<IconLabel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFEFEFEF))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        facilities.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowItems.forEach { FacilityPill(it, Modifier.weight(1f)) }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun FacilityPill(
    item: IconLabel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = item.text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    @DrawableRes iconRes: Int,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(containerColor)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
            color = contentColor,
            maxLines = 1
        )
    }
}

/**
 * Collapsed: cuts the text at [collapsedMaxLines] and appends "… See more" inline.
 * Tap to expand.
 */
@Composable
private fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLines: Int = 4
) {
    var expanded by remember { mutableStateOf(false) }
    var clipped by remember(text) { mutableStateOf<String?>(null) }
    val seeMoreColor = MaterialTheme.colorScheme.onBackground

    Text(
        text = buildAnnotatedString {
            val cut = clipped
            if (!expanded && cut != null) {
                append(cut)
                append("… ")
                withStyle(SpanStyle(color = seeMoreColor)) { append("See more") }
            } else {
                append(text)
            }
        },
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 18.sp),
        color = MaterialTheme.colorScheme.outline,
        maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
        onTextLayout = { result ->
            if (!expanded && clipped == null && result.hasVisualOverflow) {
                val end = result.getLineEnd(collapsedMaxLines - 1, visibleEnd = true)
                // leave room for "… See more" (10 chars) plus a small margin
                clipped = text.substring(0, (end - 12).coerceAtLeast(0)).trimEnd()
            }
        },
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            enabled = !expanded
        ) { expanded = true }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResortDetailScreenPreview() {
    TraveoTheme {
        ResortDetailScreen()
    }
}