package com.traveo.app.feature.profile.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

@Composable
fun SettingsSecurityScreen(
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    var faceIdEnabled by remember { mutableStateOf(true) }
    var twoFactorEnabled by remember { mutableStateOf(true) }
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var emailOffersEnabled by remember { mutableStateOf(false) }

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
                    text = "Settings & Security",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Account
            SectionLabel(text = "Account")
            Spacer(modifier = Modifier.height(14.dp))
            SettingsCard {
                NavigationRow(
                    title = "Change password",
                    value = "Updated 3 mo ago",
                    onClick = { onItemClick("Change password") }
                )
                RowDivider()
                NavigationRow(
                    title = "Email address",
                    value = "Johndeo@gmail.com",
                    onClick = { onItemClick("Email address") }
                )
                RowDivider()
                NavigationRow(
                    title = "Phone number",
                    value = "+380 67 ··· 03",
                    onClick = { onItemClick("Phone number") }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Security
            SectionLabel(text = "Security")
            Spacer(modifier = Modifier.height(14.dp))
            SettingsCard {
                ToggleRow(
                    title = "Face ID sign-in",
                    subtitle = "Unlock the app with biometrics",
                    checked = faceIdEnabled,
                    onCheckedChange = { faceIdEnabled = it }
                )
                RowDivider()
                ToggleRow(
                    title = "Two-factor authentication",
                    subtitle = "Code by SMS on new devices",
                    checked = twoFactorEnabled,
                    onCheckedChange = { twoFactorEnabled = it }
                )
                RowDivider()
                NavigationRow(
                    title = "Trusted devices",
                    value = "3 active",
                    onClick = { onItemClick("Trusted devices") }
                )
                RowDivider()
                NavigationRow(
                    title = "Login activity",
                    onClick = { onItemClick("Login activity") }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Notifications
            SectionLabel(text = "Notifications")
            Spacer(modifier = Modifier.height(14.dp))
            SettingsCard {
                ToggleRow(
                    title = "Push notifications",
                    checked = pushNotificationsEnabled,
                    onCheckedChange = { pushNotificationsEnabled = it }
                )
                RowDivider()
                ToggleRow(
                    title = "Email offers",
                    checked = emailOffersEnabled,
                    onCheckedChange = { emailOffersEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Account actions
            SettingsCard {
                ActionRow(
                    title = "Log out",
                    color = MaterialTheme.colorScheme.onBackground,
                    onClick = { onItemClick("Log out") }
                )
                RowDivider()
                ActionRow(
                    title = "Delete account",
                    color = MaterialTheme.colorScheme.error,
                    onClick = { onItemClick("Delete account") }
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

@Composable
private fun NavigationRow(
    title: String,
    value: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 17.dp),
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
            Spacer(modifier = Modifier.width(6.dp))
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Open",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun ToggleRow(
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Fixed 32dp height so Switch's 48dp touch target doesn't inflate the row
        Box(modifier = Modifier.height(32.dp), contentAlignment = Alignment.Center) {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                thumbContent = {}, // keeps the thumb at 24dp in the unchecked state too
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedBorderColor = Color.Transparent,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
private fun ActionRow(
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 22.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsSecurityScreenPreview() {
    TraveoTheme {
        SettingsSecurityScreen()
    }
}