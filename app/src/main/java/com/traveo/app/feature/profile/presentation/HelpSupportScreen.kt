package com.traveo.app.feature.profile.presentation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private data class FaqItem(
    val question: String,
    val answer: String
)

private val FaqItems = listOf(
    FaqItem(
        question = "How do I cancel a booking?",
        answer = "Open the trip in My Trips, tap Manage booking and choose Cancel. Free cancellation is available within 24 hours of purchase on Pro, and on fares marked “Refundable”."
    ),
    // Placeholder answers for the two collapsed items; replace with real copy
    FaqItem(
        question = "When will my refund arrive?",
        answer = "Refunds go back to your original payment method. Most arrive within 5–10 business days after the cancellation is confirmed."
    ),
    FaqItem(
        question = "Can I change a passenger name?",
        answer = "Small corrections can be made from Manage booking. For a different passenger, contact support so we can check the airline's rules."
    )
)

@Composable
fun HelpSupportScreen(
    onBackClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    var searchProblem by remember { mutableStateOf("") }
    var expandedFaqIndex by remember { mutableStateOf<Int?>(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(16.dp),
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
                    text = "Help & Support",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Search field
            BasicTextField(
                value = searchProblem,
                onValueChange = { searchProblem = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surface),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
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
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchProblem.isEmpty()) {
                                Text(
                                    text = "Describe your problem",
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

            Spacer(modifier = Modifier.height(20.dp))

            // Contact cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ContactCard(
                    iconRes = R.drawable.ic_chat,
                    title = "Live chat",
                    subtitle = "Replies in ~2 min",
                    highlighted = true,
                    modifier = Modifier.weight(1f)
                )
                ContactCard(
                    iconRes = R.drawable.ic_email_outline,
                    title = "Email us",
                    subtitle = "Within 24 hours",
                    highlighted = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Popular questions
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionLabel(text = "Popular questions")

                SettingsCard {
                    FaqItems.forEachIndexed { index, item ->
                        FaqRow(
                            item = item,
                            expanded = expandedFaqIndex == index,
                            onClick = {
                                expandedFaqIndex = if (expandedFaqIndex == index) null else index
                            }
                        )
                        RowDivider()
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(horizontal = 18.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "All help articles",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = "Open",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // More
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionLabel(text = "More")

                SettingsCard {
                    MoreRow(title = "Report a problem with a trip")
                    RowDivider()
                    MoreRow(
                        title = "Call support · 24/7",
                        value = "+1 888 402 11 90",
                        showChevron = false
                    )
                    RowDivider()
                    MoreRow(title = "Terms & Privacy", onClick = onPrivacyPolicyClick)
                }
            }
        }
    }
}

@Composable
private fun ContactCard(
    @DrawableRes iconRes: Int,
    title: String,
    subtitle: String,
    highlighted: Boolean,
    modifier: Modifier = Modifier
) {
    val primary = MaterialTheme.colorScheme.primary
    val backgroundModifier = if (highlighted) {
        Modifier.background(Brush.linearGradient(listOf(primary, primary.copy(alpha = 0.7f))))
    } else {
        Modifier.background(MaterialTheme.colorScheme.surface)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .then(backgroundModifier)
            .clickable { }
            .padding(18.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint = if (highlighted) Color.White else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500, fontSize = 15.sp),
            color = if (highlighted) Color.White else MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = if (highlighted) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun FaqRow(
    item: FaqItem,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.question,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            ExpandIcon(expanded = expanded)
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.answer,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 22.sp,
                    fontSize = 13.sp,
                ),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

/** Thin "+" / "−" drawn with boxes so no extra drawables are needed. */
@Composable
private fun ExpandIcon(expanded: Boolean) {
    val color = MaterialTheme.colorScheme.outline

    Box(
        modifier = Modifier.size(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(14.dp)
                .height(1.5.dp)
                .background(color)
        )
        if (!expanded) {
            Box(
                modifier = Modifier
                    .width(1.5.dp)
                    .height(14.dp)
                    .background(color)
            )
        }
    }
}

@Composable
private fun MoreRow(
    title: String,
    value: String? = null,
    showChevron: Boolean = true,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }

        if (showChevron) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(18.dp)
            )
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

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HelpSupportScreenPreview() {
    TraveoTheme {
        HelpSupportScreen()
    }
}