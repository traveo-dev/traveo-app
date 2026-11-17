package com.traveo.app.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme
import java.util.Locale

/**
 * Smaller card for horizontal lists (Search screen).
 * Uses the same [ResortItem] as [FeaturedResortCard].
 */
@Composable
fun CompactResortCard(
    resort: ResortItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(232.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
    ) {
        // Image with overlays
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(24.dp))
        ) {
            Image(
                painter = painterResource(id = resort.imageRes),
                contentDescription = resort.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Location badge
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${resort.city}, ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = resort.country,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            // Arrow button
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up_right),
                    contentDescription = "Details",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }

            // Image dots
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = if (index == 0) 1f else 0.6f))
                    )
                }
            }
        }

        // Details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = resort.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400
                ),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location_outline),
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(12.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = resort.address,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_up_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val fullStars = resort.rating.toInt().coerceIn(0, 5)
                    val hasHalfStar = fullStars < 5 && resort.rating - fullStars >= 0.5f

                    repeat(fullStars) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_round),
                            contentDescription = null,
                            tint = Color(0xFFFFB909),
                            modifier = Modifier.size(11.dp)
                        )
                    }
                    if (hasHalfStar) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_half),
                            contentDescription = null,
                            tint = Color(0xFFFFB909),
                            modifier = Modifier.size(11.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = String.format(Locale.US, "%.1f", resort.rating),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                // Price
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "From ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = resort.price,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = " Only",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CompactResortCardPreview() {
    TraveoTheme {
        CompactResortCard(
            resort = SampleResorts[1],
            modifier = Modifier.padding(16.dp)
        )
    }
}