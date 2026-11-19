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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private val ProFeatures = listOf(
    "Member fares up to 20% cheaper",
    "Free cancellation within 24 h",
    "Priority 24/7 support",
    "Two free seat upgrades a year"
)

@Composable
fun UpgradeProScreen(
    onBackClick: () -> Unit = {},
    onStartTrialClick: () -> Unit = {}
) {
    var selectedPlanIndex by remember { mutableIntStateOf(0) } // 0: Yearly, 1: Monthly

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Top Bar Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Upgrade to Pro",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(44.dp))
            }

            // Blue Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(24.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sparkle),
                        contentDescription = "Sparkle",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )

                    Text(
                        text = "Save up to 20% on every trip",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontSize = 22.sp
                    )

                    Text(
                        text = "Pro members get member fares, free cancellation and priority support on all bookings.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            // Plan Options Row (Yearly vs Monthly)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Yearly Option Card
                val isYearlySelected = selectedPlanIndex == 0
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedPlanIndex = 0 },
                    color = if (isYearlySelected) MaterialTheme.colorScheme.primary else Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Yearly",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isYearlySelected) Color.White.copy(alpha = 0.8f) else Color.Gray,
                                fontSize = 12.sp
                            )
                            RadioButton(
                                selected = isYearlySelected,
                                onClick = { selectedPlanIndex = 0 },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.White,
                                    unselectedColor = Color.LightGray
                                )
                            )
                        }

                        Text(
                            text = "$79.99",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (isYearlySelected) Color.White else MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = "$6.66 / month · save 33%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isYearlySelected) Color.White.copy(alpha = 0.9f) else Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }

                // Monthly Option Card
                val isMonthlySelected = selectedPlanIndex == 1
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { selectedPlanIndex = 1 },
                    color = if (isMonthlySelected) MaterialTheme.colorScheme.primary else Color.White,
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Monthly",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isMonthlySelected) Color.White.copy(alpha = 0.8f) else Color.Gray,
                                fontSize = 12.sp
                            )
                            RadioButton(
                                selected = isMonthlySelected,
                                onClick = { selectedPlanIndex = 1 },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.White,
                                    unselectedColor = Color.LightGray
                                )
                            )
                        }

                        Text(
                            text = "$9.99",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (isMonthlySelected) Color.White else MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = "Billed every month",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isMonthlySelected) Color.White.copy(alpha = 0.9f) else Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Features List Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column {
                    ProFeatures.forEachIndexed { index, feature ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE3F2FD)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "✓", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text = feature,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        if (index < ProFeatures.lastIndex) {
                            HorizontalDivider(color = Color(0x0F000000))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Button & Subtext
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onStartTrialClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Start 7-day free trial",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }

                Text(
                    text = "Then $79.99 per year. Cancel anytime in Plan & Subscription.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpgradeProScreenPreview() {
    TraveoTheme {
        UpgradeProScreen()
    }
}
