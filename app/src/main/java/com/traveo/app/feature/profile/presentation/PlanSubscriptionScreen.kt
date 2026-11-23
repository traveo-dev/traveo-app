package com.traveo.app.feature.profile.presentation

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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private data class PlanOption(
    val title: String,
    val price: String,
    val caption: String,
    val footnote: String
)

private val Plans = listOf(
    PlanOption(
        title = "Yearly",
        price = "$79.99",
        caption = "$6.66 / month · save 33%",
        footnote = "Then $79.99 per year. Cancel anytime in Plan & Subscription."
    ),
    PlanOption(
        title = "Monthly",
        price = "$9.99",
        caption = "Billed every month",
        footnote = "Then $9.99 per month. Cancel anytime in Plan & Subscription."
    )
)

private val Benefits = listOf(
    "Member fares up to 20% cheaper",
    "Free cancellation within 24 h",
    "Priority 24/7 support",
    "Two free seat upgrades a year"
)

@Composable
fun PlanSubscriptionScreen(
    onBackClick: () -> Unit = {},
    onUpgradeClick: () -> Unit = {}
) {
    var selectedPlanIndex by remember { mutableIntStateOf(0) }
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
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            ) {
                // Header
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
                            .clickable(onClick = onBackClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = "Upgrade to Pro",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Hero card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = primary.copy(alpha = 0.4f),
                            spotColor = primary.copy(alpha = 0.4f)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(primary)
                        .padding(horizontal = 22.dp, vertical = 24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sparkle),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Save up to 20% on every trip",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Pro members get member fares, free cancellation and priority support on all bookings.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        ),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))

                // Plan selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Plans.forEachIndexed { index, plan ->
                        PlanCard(
                            plan = plan,
                            selected = selectedPlanIndex == index,
                            onClick = { selectedPlanIndex = index },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(13.dp))

                // Benefits
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Benefits.forEachIndexed { index, benefit ->
                        BenefitRow(text = benefit)

                        if (index != Benefits.lastIndex) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)
                            )
                        }
                    }
                }
            }

            // Pinned CTA
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(listOf(primary, primary.copy(alpha = 0.7f)))
                        )
                        .clickable(onClick = onUpgradeClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Start 7-day free trial",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = Color.White
                    )
                }

                Text(
                    text = Plans[selectedPlanIndex].footnote,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PlanCard(
    plan: PlanOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
    val mutedColor = if (selected) Color.White.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = plan.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                color = mutedColor
            )
            PlanRadio(selected = selected)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = plan.price,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W400
            ),
            color = contentColor,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = plan.caption,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
            color = if (selected) Color.White else MaterialTheme.colorScheme.outline,
            maxLines = 1
        )
    }
}

@Composable
private fun PlanRadio(selected: Boolean) {
    val ringColor = if (selected) {
        Color.White.copy(alpha = 0.5f)
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    }

    Box(
        modifier = Modifier
            .size(20.dp)
            .border(1.5.dp, ringColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.6f))
            )
        }
    }
}

@Composable
private fun BenefitRow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 19.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlanSubscriptionScreenPreview() {
    TraveoTheme {
        PlanSubscriptionScreen()
    }
}