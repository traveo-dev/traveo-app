package com.traveo.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
    val screenStack = remember { mutableStateListOf(Screen.HOME) }
    val currentScreen = screenStack.last()
    val context = LocalContext.current

    fun navigateTo(screen: Screen) {
        screenStack.add(screen)
    }

    fun popBack() {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.lastIndex)
        }
    }

    // Intercept System Hardware / Gesture Back Press
    BackHandler(enabled = screenStack.size > 1) {
        popBack()
    }

    when (currentScreen) {
        Screen.WELCOME -> {
            WelcomeScreen(
                onSignInClick = { navigateTo(Screen.LOGIN_PHONE) },
                onGoogleSignInClick = {
                    screenStack.clear()
                    screenStack.add(Screen.HOME)
                }
            )
        }
        Screen.LOGIN_PHONE -> {
            LoginScreen(
                onBackClick = { popBack() },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { dialCode, phoneNumber -> navigateTo(Screen.OTP_PHONE) },
                onGoogleSignInClick = {
                    screenStack.clear()
                    screenStack.add(Screen.HOME)
                },
                onEmailSignInClick = { navigateTo(Screen.LOGIN_EMAIL) }
            )
        }
        Screen.LOGIN_EMAIL -> {
            EmailLoginScreen(
                onBackClick = { popBack() },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { email -> navigateTo(Screen.OTP_EMAIL) },
                onGoogleSignInClick = {
                    screenStack.clear()
                    screenStack.add(Screen.HOME)
                },
                onPhoneSignInClick = { navigateTo(Screen.LOGIN_PHONE) }
            )
        }
        Screen.OTP_PHONE -> {
            OtpScreen(
                otpType = OtpType.PHONE,
                onBackClick = { popBack() },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { otpCode ->
                    screenStack.clear()
                    screenStack.add(Screen.HOME)
                }
            )
        }
        Screen.OTP_EMAIL -> {
            OtpScreen(
                otpType = OtpType.EMAIL,
                onBackClick = { popBack() },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { otpCode ->
                    screenStack.clear()
                    screenStack.add(Screen.HOME)
                }
            )
        }
        Screen.HOME -> {
            MainScreen(
                onSearchClick = { navigateTo(Screen.SEARCH) },
                onResortClick = { navigateTo(Screen.RESORT_DETAIL) },
                onProfileSearchClick = { navigateTo(Screen.SEARCH_PROFILE) },
                onProfileUpgradeClick = { navigateTo(Screen.UPGRADE_PRO) },
                onProfileSettingClick = { settingName ->
                    val targetScreen = when (settingName) {
                        "Profile Details" -> Screen.EDIT_PROFILE
                        "Settings & Security" -> Screen.SETTINGS_SECURITY
                        "Plan & Subscription" -> Screen.PLAN_SUBSCRIPTION
                        "Passengers & Documents" -> Screen.PASSENGERS_DOCS
                        "Travel Preferences" -> Screen.TRAVEL_PREFERENCES
                        "Payment Methods" -> Screen.PAYMENT_METHODS
                        "Language & Currency" -> Screen.LANGUAGE_CURRENCY
                        "Help & Support" -> Screen.HELP_SUPPORT
                        "Privacy Policy" -> Screen.PRIVACY_POLICY
                        else -> null
                    }
                    if (targetScreen != null) {
                        navigateTo(targetScreen)
                    }
                }
            )
        }
        Screen.SEARCH -> {
            SearchScreen(
                onBackClick = { popBack() },
                onResortClick = { navigateTo(Screen.RESORT_DETAIL) }
            )
        }
        Screen.RESORT_DETAIL -> {
            ResortDetailScreen(
                onBackClick = { popBack() },
                onCallClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+62361701888"))
                    context.startActivity(intent)
                },
                onBookClick = { }
            )
        }
        Screen.SEARCH_PROFILE -> {
            SearchProfileScreen(
                onBackClick = { popBack() },
                onResultClick = { result ->
                    when (result) {
                        "Payment Methods" -> navigateTo(Screen.PAYMENT_METHODS)
                        "Plan & Subscription" -> navigateTo(Screen.PLAN_SUBSCRIPTION)
                    }
                }
            )
        }
        Screen.EDIT_PROFILE -> {
            EditProfileScreen(
                onBackClick = { popBack() },
                onSaveClick = { popBack() }
            )
        }
        Screen.UPGRADE_PRO -> {
            UpgradeProScreen(
                onBackClick = { popBack() },
                onStartTrialClick = { popBack() }
            )
        }
        Screen.SETTINGS_SECURITY -> {
            SettingsSecurityScreen(
                onBackClick = { popBack() }
            )
        }
        Screen.PLAN_SUBSCRIPTION -> {
            PlanSubscriptionScreen(
                onBackClick = { popBack() },
                onUpgradeClick = { navigateTo(Screen.UPGRADE_PRO) },
                onBillingClick = { item ->
                    if (item == "Payment method") navigateTo(Screen.PAYMENT_METHODS)
                }
            )
        }
        Screen.PASSENGERS_DOCS -> {
            PassengersDocsScreen(
                onBackClick = { popBack() }
            )
        }
        Screen.TRAVEL_PREFERENCES -> {
            TravelPreferencesScreen(
                onBackClick = { popBack() }
            )
        }
        Screen.PAYMENT_METHODS -> {
            PaymentMethodsScreen(
                onBackClick = { popBack() }
            )
        }
        Screen.LANGUAGE_CURRENCY -> {
            LanguageCurrencyScreen(
                onBackClick = { popBack() }
            )
        }
        Screen.HELP_SUPPORT -> {
            HelpSupportScreen(
                onBackClick = { popBack() },
                onPrivacyPolicyClick = { navigateTo(Screen.PRIVACY_POLICY) }
            )
        }
        Screen.PRIVACY_POLICY -> {
            PrivacyPolicyScreen(
                onBackClick = { popBack() }
            )
        }
        Screen.CREATE_TRIP_DATES -> {
            CreateTripDatesScreen(
                onBackClick = { popBack() },
                onContinueClick = { navigateTo(Screen.CREATE_TRIP_CITIES) }
            )
        }
        Screen.CREATE_TRIP_CITIES -> {
            CreateTripCitiesScreen(
                onBackClick = { popBack() },
                onContinueClick = { navigateTo(Screen.INVITE_FRIENDS) }
            )
        }
        Screen.INVITE_FRIENDS -> {
            InviteFriendsScreen(
                onBackClick = { popBack() },
                onDoneClick = { navigateTo(Screen.TRIP_DETAIL) }
            )
        }
        Screen.TRIP_DETAIL -> {
            TripDetailScreen(
                onBackClick = { popBack() },
                onMembersClick = { navigateTo(Screen.TRIP_MEMBERS) },
                onAddExpenseClick = { navigateTo(Screen.ADD_EXPENSE) },
                onAddEventClick = { navigateTo(Screen.ADD_TRANSPORT) },
                onEventClick = { navigateTo(Screen.EVENT_DETAIL) }
            )
        }
        Screen.TRIP_MEMBERS -> {
            TripMembersScreen(
                onBackClick = { popBack() },
                onInviteMoreClick = { navigateTo(Screen.INVITE_FRIENDS) }
            )
        }
        Screen.TRIP_BUDGET -> {
            TripBudgetScreen(
                onBackClick = { popBack() },
                onAddExpenseClick = { navigateTo(Screen.ADD_EXPENSE) }
            )
        }
        Screen.ADD_EXPENSE -> {
            AddExpenseScreen(
                onBackClick = { popBack() },
                onAddExpenseClick = { popBack() }
            )
        }
        Screen.TRIP_TIMELINE -> {
            TripTimelineScreen(
                onBackClick = { popBack() },
                onAddEventClick = { navigateTo(Screen.ADD_TRANSPORT) },
                onEventClick = { navigateTo(Screen.EVENT_DETAIL) }
            )
        }
        Screen.TRIP_MAP -> {
            TripMapScreen(
                onBackClick = { popBack() },
                onBuildRouteClick = { navigateTo(Screen.EVENT_DETAIL) }
            )
        }
        Screen.ADD_TRANSPORT -> {
            AddTransportScreen(
                onBackClick = { popBack() },
                onAddClick = { popBack() }
            )
        }
        Screen.EVENT_DETAIL -> {
            EventDetailScreen(
                onBackClick = { popBack() },
                onHowToGetThereClick = { navigateTo(Screen.DIRECTIONS) },
                onEditEventClick = { navigateTo(Screen.ADD_TRANSPORT) }
            )
        }
        Screen.DIRECTIONS -> {
            DirectionsScreen(
                onBackClick = { popBack() },
                onSaveToTripClick = { popBack() }
            )
        }
    }
}
