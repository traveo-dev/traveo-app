package com.traveo.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.traveo.app.core.designsystem.theme.TraveoTheme
import com.traveo.app.feature.auth.presentation.login.EmailLoginScreen
import com.traveo.app.feature.auth.presentation.login.LoginScreen
import com.traveo.app.feature.auth.presentation.otp.OtpScreen
import com.traveo.app.feature.auth.presentation.otp.OtpType
import com.traveo.app.feature.auth.presentation.welcome.WelcomeScreen
import com.traveo.app.feature.home.presentation.ResortDetailScreen
import com.traveo.app.feature.home.presentation.SearchScreen
import com.traveo.app.feature.main.presentation.MainScreen
import com.traveo.app.feature.profile.presentation.EditProfileScreen
import com.traveo.app.feature.profile.presentation.HelpSupportScreen
import com.traveo.app.feature.profile.presentation.LanguageCurrencyScreen
import com.traveo.app.feature.profile.presentation.PassengersDocsScreen
import com.traveo.app.feature.profile.presentation.PaymentMethodsScreen
import com.traveo.app.feature.profile.presentation.PlanSubscriptionScreen
import com.traveo.app.feature.profile.presentation.PrivacyPolicyScreen
import com.traveo.app.feature.profile.presentation.SearchProfileScreen
import com.traveo.app.feature.profile.presentation.SettingsSecurityScreen
import com.traveo.app.feature.profile.presentation.TravelPreferencesScreen
import com.traveo.app.feature.profile.presentation.UpgradeProScreen
import com.traveo.app.feature.trips.presentation.AddExpenseScreen
import com.traveo.app.feature.trips.presentation.AddTransportScreen
import com.traveo.app.feature.trips.presentation.CreateTripCitiesScreen
import com.traveo.app.feature.trips.presentation.CreateTripDatesScreen
import com.traveo.app.feature.trips.presentation.DirectionsScreen
import com.traveo.app.feature.trips.presentation.EventDetailScreen
import com.traveo.app.feature.trips.presentation.InviteFriendsScreen
import com.traveo.app.feature.trips.presentation.TripBudgetScreen
import com.traveo.app.feature.trips.presentation.TripDetailScreen
import com.traveo.app.feature.trips.presentation.TripMapScreen
import com.traveo.app.feature.trips.presentation.TripMembersScreen
import com.traveo.app.feature.trips.presentation.TripTimelineScreen

private enum class Screen {
    WELCOME,
    LOGIN_PHONE,
    LOGIN_EMAIL,
    OTP_PHONE,
    OTP_EMAIL,
    HOME,
    SEARCH,
    RESORT_DETAIL,
    SEARCH_PROFILE,
    EDIT_PROFILE,
    UPGRADE_PRO,
    SETTINGS_SECURITY,
    PLAN_SUBSCRIPTION,
    PASSENGERS_DOCS,
    TRAVEL_PREFERENCES,
    PAYMENT_METHODS,
    LANGUAGE_CURRENCY,
    HELP_SUPPORT,
    PRIVACY_POLICY,
    CREATE_TRIP_DATES,
    CREATE_TRIP_CITIES,
    INVITE_FRIENDS,
    TRIP_DETAIL,
    TRIP_MEMBERS,
    TRIP_BUDGET,
    ADD_EXPENSE,
    TRIP_TIMELINE,
    TRIP_MAP,
    ADD_TRANSPORT,
    EVENT_DETAIL,
    DIRECTIONS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TraveoTheme {
                TraveoAppNavigation()
            }
        }
    }
}

@Composable
fun TraveoAppNavigation() {
    var currentScreen by remember { mutableStateOf(Screen.WELCOME) }
    val context = LocalContext.current

    when (currentScreen) {
        Screen.WELCOME -> {
            WelcomeScreen(
                onSignInClick = { currentScreen = Screen.LOGIN_PHONE },
                onGoogleSignInClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.LOGIN_PHONE -> {
            LoginScreen(
                onBackClick = { currentScreen = Screen.WELCOME },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { dialCode, phoneNumber -> currentScreen = Screen.OTP_PHONE },
                onGoogleSignInClick = { currentScreen = Screen.HOME },
                onEmailSignInClick = { currentScreen = Screen.LOGIN_EMAIL }
            )
        }
        Screen.LOGIN_EMAIL -> {
            EmailLoginScreen(
                onBackClick = { currentScreen = Screen.LOGIN_PHONE },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { email -> currentScreen = Screen.OTP_EMAIL },
                onGoogleSignInClick = { currentScreen = Screen.HOME },
                onPhoneSignInClick = { currentScreen = Screen.LOGIN_PHONE }
            )
        }
        Screen.OTP_PHONE -> {
            OtpScreen(
                otpType = OtpType.PHONE,
                onBackClick = { currentScreen = Screen.LOGIN_PHONE },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { otpCode -> currentScreen = Screen.HOME }
            )
        }
        Screen.OTP_EMAIL -> {
            OtpScreen(
                otpType = OtpType.EMAIL,
                onBackClick = { currentScreen = Screen.LOGIN_EMAIL },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { otpCode -> currentScreen = Screen.HOME }
            )
        }
        Screen.HOME -> {
            MainScreen(
                onSearchClick = { currentScreen = Screen.SEARCH },
                onResortClick = { currentScreen = Screen.RESORT_DETAIL },
                onProfileSearchClick = { currentScreen = Screen.SEARCH_PROFILE },
                onProfileUpgradeClick = { currentScreen = Screen.UPGRADE_PRO },
                onProfileSettingClick = { settingName ->
                    currentScreen = when (settingName) {
                        "Profile Details" -> Screen.EDIT_PROFILE
                        "Settings & Security" -> Screen.SETTINGS_SECURITY
                        "Plan & Subscription" -> Screen.PLAN_SUBSCRIPTION
                        "Passengers & Documents" -> Screen.PASSENGERS_DOCS
                        "Travel Preferences" -> Screen.TRAVEL_PREFERENCES
                        "Payment Methods" -> Screen.PAYMENT_METHODS
                        "Language & Currency" -> Screen.LANGUAGE_CURRENCY
                        "Help & Support" -> Screen.HELP_SUPPORT
                        "Privacy Policy" -> Screen.PRIVACY_POLICY
                        else -> Screen.HOME
                    }
                }
            )
        }
        Screen.SEARCH -> {
            SearchScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onResortClick = { currentScreen = Screen.RESORT_DETAIL }
            )
        }
        Screen.RESORT_DETAIL -> {
            ResortDetailScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onCallClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+62361701888"))
                    context.startActivity(intent)
                },
                onBookClick = { }
            )
        }
        Screen.SEARCH_PROFILE -> {
            SearchProfileScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onResultClick = { result ->
                    currentScreen = when (result) {
                        "Payment Methods" -> Screen.PAYMENT_METHODS
                        "Plan & Subscription" -> Screen.PLAN_SUBSCRIPTION
                        else -> Screen.HOME
                    }
                }
            )
        }
        Screen.EDIT_PROFILE -> {
            EditProfileScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onSaveClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.UPGRADE_PRO -> {
            UpgradeProScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onStartTrialClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.SETTINGS_SECURITY -> {
            SettingsSecurityScreen(
                onBackClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.PLAN_SUBSCRIPTION -> {
            PlanSubscriptionScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onUpgradeClick = { currentScreen = Screen.UPGRADE_PRO },
                onBillingClick = { item ->
                    if (item == "Payment method") currentScreen = Screen.PAYMENT_METHODS
                }
            )
        }
        Screen.PASSENGERS_DOCS -> {
            PassengersDocsScreen(
                onBackClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.TRAVEL_PREFERENCES -> {
            TravelPreferencesScreen(
                onBackClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.PAYMENT_METHODS -> {
            PaymentMethodsScreen(
                onBackClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.LANGUAGE_CURRENCY -> {
            LanguageCurrencyScreen(
                onBackClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.HELP_SUPPORT -> {
            HelpSupportScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onPrivacyPolicyClick = { currentScreen = Screen.PRIVACY_POLICY }
            )
        }
        Screen.PRIVACY_POLICY -> {
            PrivacyPolicyScreen(
                onBackClick = { currentScreen = Screen.HOME }
            )
        }
        Screen.CREATE_TRIP_DATES -> {
            CreateTripDatesScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onContinueClick = { currentScreen = Screen.CREATE_TRIP_CITIES }
            )
        }
        Screen.CREATE_TRIP_CITIES -> {
            CreateTripCitiesScreen(
                onBackClick = { currentScreen = Screen.CREATE_TRIP_DATES },
                onContinueClick = { currentScreen = Screen.INVITE_FRIENDS }
            )
        }
        Screen.INVITE_FRIENDS -> {
            InviteFriendsScreen(
                onBackClick = { currentScreen = Screen.CREATE_TRIP_CITIES },
                onDoneClick = { currentScreen = Screen.TRIP_DETAIL }
            )
        }
        Screen.TRIP_DETAIL -> {
            TripDetailScreen(
                onBackClick = { currentScreen = Screen.HOME },
                onMembersClick = { currentScreen = Screen.TRIP_MEMBERS },
                onAddExpenseClick = { currentScreen = Screen.ADD_EXPENSE },
                onAddEventClick = { currentScreen = Screen.ADD_TRANSPORT },
                onEventClick = { currentScreen = Screen.EVENT_DETAIL }
            )
        }
        Screen.TRIP_MEMBERS -> {
            TripMembersScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onInviteMoreClick = { currentScreen = Screen.INVITE_FRIENDS }
            )
        }
        Screen.TRIP_BUDGET -> {
            TripBudgetScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onAddExpenseClick = { currentScreen = Screen.ADD_EXPENSE }
            )
        }
        Screen.ADD_EXPENSE -> {
            AddExpenseScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onAddExpenseClick = { currentScreen = Screen.TRIP_DETAIL }
            )
        }
        Screen.TRIP_TIMELINE -> {
            TripTimelineScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onAddEventClick = { currentScreen = Screen.ADD_TRANSPORT },
                onEventClick = { currentScreen = Screen.EVENT_DETAIL }
            )
        }
        Screen.TRIP_MAP -> {
            TripMapScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onBuildRouteClick = { currentScreen = Screen.EVENT_DETAIL }
            )
        }
        Screen.ADD_TRANSPORT -> {
            AddTransportScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onAddClick = { currentScreen = Screen.TRIP_DETAIL }
            )
        }
        Screen.EVENT_DETAIL -> {
            EventDetailScreen(
                onBackClick = { currentScreen = Screen.TRIP_DETAIL },
                onHowToGetThereClick = { currentScreen = Screen.DIRECTIONS },
                onEditEventClick = { currentScreen = Screen.ADD_TRANSPORT }
            )
        }
        Screen.DIRECTIONS -> {
            DirectionsScreen(
                onBackClick = { currentScreen = Screen.EVENT_DETAIL },
                onSaveToTripClick = { currentScreen = Screen.TRIP_DETAIL }
            )
        }
    }
}
