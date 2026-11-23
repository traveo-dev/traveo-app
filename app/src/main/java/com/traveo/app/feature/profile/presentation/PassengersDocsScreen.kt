package com.traveo.app.feature.profile.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.SuccessGreen
import com.traveo.app.core.designsystem.theme.TraveoTheme

private data class Passenger(
    @DrawableRes val avatarRes: Int,
    val name: String,
    val details: String,
    val badge: String? = null
)

private data class TravelDocument(
    val title: String,
    val holder: String,
    val expires: String,
    val status: String,
    val isWarning: Boolean = false
)

private val Passengers = listOf(
    Passenger(R.drawable.img_profile_logo, "Andrii Bielov", "Adult · Passport FE····12", "You"),
    Passenger(R.drawable.img_profile_logo, "Olena Bielov", "Adult · Passport FE····47"),
    Passenger(R.drawable.img_profile_logo, "Mark Bielov", "Child, 12 y.o. · No document")
)

private val Documents = listOf(
    TravelDocument("Passport", "Andrii Bielov · FE 445 512", "exp. 09/2031", "Valid"),
    TravelDocument("Passport", "Olena Bielov · FE 771 047", "exp. 12/2026", "Expires in 4 mo", isWarning = true),
    TravelDocument("Visa · Schengen C", "Andrii Bielov · multi-entry", "exp. 03/2028", "Valid")
)

@Composable
fun PassengersDocsScreen(
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
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
                    text = "Passengers & Docs",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )

                CircleIconButton(
                    iconRes = R.drawable.ic_add,
                    contentDescription = "Add",
                    onClick = onAddClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Saved passengers
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionLabel(text = "Saved passengers")

                SettingsCard {
                    Passengers.forEach { passenger ->
                        PassengerRow(passenger = passenger)
                        RowDivider()
                    }
                    AddPassengerRow(onClick = onAddClick)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Documents
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionLabel(text = "Documents")

                SettingsCard {
                    Documents.forEach { document ->
                        DocumentRow(document = document)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Add document (dashed)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dashedBorder(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        cornerRadius = 24.dp
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(onClick = onAddClick)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Add document",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Scan a passport, ID or visa",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Composable
private fun PassengerRow(passenger: Passenger) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = passenger.avatarRes),
            contentDescription = passenger.name,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = passenger.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                if (passenger.badge != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = passenger.badge,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W500
                            ),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = passenger.details,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Details",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun AddPassengerRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .dashedBorder(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    cornerRadius = 24.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Add passenger",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun DocumentRow(document: TravelDocument) {
    // Valid uses the theme's success color. The warning pair has no theme token yet.
    val badgeColor = if (document.isWarning) Color(0xFFFFF3E0) else SuccessGreen.copy(alpha = 0.12f)
    val badgeTextColor = if (document.isWarning) Color(0xFFE65100) else SuccessGreen

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 18.dp, vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = document.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(badgeColor)
                    .padding(horizontal = 9.dp, vertical = 4.dp)
            ) {
                Text(
                    text = document.status,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, fontWeight = FontWeight.W500),
                    color = badgeTextColor,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = document.holder,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = document.expires,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.outline
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

private fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp,
    strokeWidth: Dp = 1.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp
): Modifier = drawBehind {
    val stroke = strokeWidth.toPx()
    drawRoundRect(
        color = color,
        topLeft = Offset(stroke / 2, stroke / 2),
        size = Size(size.width - stroke, size.height - stroke),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = Stroke(
            width = stroke,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength.toPx(), gapLength.toPx()))
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PassengersDocsScreenPreview() {
    TraveoTheme {
        PassengersDocsScreen()
    }
}