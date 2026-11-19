package com.traveo.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme
import java.util.Locale

data class ResortItem(
    @DrawableRes val imageRes: Int,
    val city: String,
    val country: String,
    val name: String,
    val address: String,
    val rating: Float,
    val price: String,
    val features: List<String> = emptyList()
)

// Mock data shared by Home and Search. Replace with real data later.
val SampleResorts = listOf(
    ResortItem(
        imageRes = R.drawable.resort_the_mulia,
        city = "Bali",
        country = "Indonesia",
        name = "The Mulia",
        address = "St. Regis Bali Resort",
        rating = 4.7f,
        price = "$420",
        features = listOf("Ocean View", "2 Beds", "Spa & Wellness Center", "Private Pool")
    ),
    ResortItem(
        imageRes = R.drawable.resort_kayumanis,
        city = "Bali",
        country = "Indonesia",
        name = "Kayumanis Jimbaran Estate",
        address = "Jl. Nusa Dua Selatan, Jimbaran",
        rating = 5.0f,
        price = "$480",
        features = listOf("Garden View", "3 Beds", "Private Pool")
    ),
    ResortItem(
        imageRes = R.drawable.resort_alila,
        city = "Bali",
        country = "Indonesia",
        name = "Alila Seminyak",
        address = "Jl. Kayu Aya No. 7, Seminyak",
        rating = 5.0f,
        price = "$520",
        features = listOf("Beachfront", "2 Beds", "Rooftop Bar")
    )
)

@Composable
fun FeaturedResortCard(
    resort: ResortItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
    ) {
        Column {
            // Image with Overlay Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(254.dp)
                    .clip(RoundedCornerShape(32.dp))
            ) {
                Image(
                    painter = painterResource(id = resort.imageRes),
                    contentDescription = resort.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Location Badge
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .padding(horizontal = 18.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${resort.city}, ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = resort.country,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopEnd),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "Favorite",
                            tint = Color(0xFFFF3E3E),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_up_right),
                            contentDescription = "Details",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Resort Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = resort.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.W400,
                        fontSize = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location_outline),
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = resort.address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_up_right),
                        contentDescription = "Arrow Up Right",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                                contentDescription = "Star Rating",
                                tint = Color(0xFFFFB909),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        if (hasHalfStar) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star_half),
                                contentDescription = "Star Rating",
                                tint = Color(0xFFFFB909),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = String.format(Locale.US, "%.1f", resort.rating),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.W400
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    // Price
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "From ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = resort.price,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = " Only",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (resort.features.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        resort.features.forEach { FeatureChip(text = it) }
                    }
                }
            }
        }
    }
}

@Composable
fun FeatureChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFEFEFEF))
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.W400
            ),
            color = Color(0xFF8998B2)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun FeaturedResortCardPreview() {
    TraveoTheme {
        FeaturedResortCard(
            resort = SampleResorts[0],
            modifier = Modifier.padding(16.dp)
        )
    }
}