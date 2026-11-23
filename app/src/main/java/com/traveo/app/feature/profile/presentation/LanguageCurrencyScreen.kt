package com.traveo.app.feature.profile.presentation

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

private data class LanguageOption(
    val name: String,
    val nativeName: String
)

private data class CurrencyOption(
    val symbol: String,
    val name: String
)

private val Languages = listOf(
    LanguageOption("English", "English"),
    LanguageOption("Ukrainian", "Українська"),
    LanguageOption("German", "Deutsch"),
    LanguageOption("Spanish", "Español"),
    LanguageOption("French", "Français")
)

private val Currencies = listOf(
    CurrencyOption("$", "US Dollar"),
    CurrencyOption("€", "Euro"),
    CurrencyOption("₴", "Ukrainian Hryvnia"),
    CurrencyOption("£", "British Pound")
)

@Composable
fun LanguageCurrencyScreen(
    onBackClick: () -> Unit = {}
) {
    var selectedLanguageIndex by remember { mutableIntStateOf(0) }
    var selectedCurrencyIndex by remember { mutableIntStateOf(0) }

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
                    text = "Language & Currency",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // App language
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionLabel(text = "App language")

                SettingsCard {
                    Languages.forEachIndexed { index, language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedLanguageIndex = index }
                                .padding(horizontal = 18.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = language.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = language.nativeName,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            SelectionIndicator(selected = selectedLanguageIndex == index)
                        }

                        if (index != Languages.lastIndex) RowDivider()
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Currency
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SectionLabel(text = "Currency")

                SettingsCard {
                    Currencies.forEachIndexed { index, currency ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCurrencyIndex = index }
                                .padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = currency.symbol,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                ),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.width(40.dp)
                            )

                            Text(
                                text = currency.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.weight(1f)
                            )

                            SelectionIndicator(selected = selectedCurrencyIndex == index)
                        }

                        if (index != Currencies.lastIndex) RowDivider()
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Formats
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionLabel(text = "Formats")

                SettingsCard {
                    NavigationRow(title = "Date format", value = "DD.MM.YYYY")
                    RowDivider()
                    NavigationRow(title = "Time format", value = "24-hour")
                    RowDivider()
                    NavigationRow(title = "Units", value = "Metric · km")
                }
            }
        }
    }
}

@Composable
private fun SelectionIndicator(selected: Boolean) {
    if (selected) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(22.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), CircleShape)
        )
    }
}

@Composable
private fun NavigationRow(
    title: String,
    value: String? = null,
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
            Spacer(modifier = Modifier.width(8.dp))
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
fun LanguageCurrencyScreenPreview() {
    TraveoTheme {
        LanguageCurrencyScreen()
    }
}