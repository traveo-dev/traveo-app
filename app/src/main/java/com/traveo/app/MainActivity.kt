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
import com.traveo.app.feature.home.presentation.HomeScreen
import com.traveo.app.feature.home.presentation.ResortDetailScreen
import com.traveo.app.feature.home.presentation.SearchScreen

private enum class Screen {
    WELCOME,
    LOGIN_PHONE,
    LOGIN_EMAIL,
    OTP_PHONE,
    OTP_EMAIL,
    HOME,
    SEARCH,
    RESORT_DETAIL
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
                onSignInClick = {
                    currentScreen = Screen.LOGIN_PHONE
                },
                onGoogleSignInClick = {
                    currentScreen = Screen.HOME
                }
            )
        }
        Screen.LOGIN_PHONE -> {
            LoginScreen(
                onBackClick = {
                    currentScreen = Screen.WELCOME
                },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { dialCode, phoneNumber ->
                    currentScreen = Screen.OTP_PHONE
                },
                onGoogleSignInClick = {
                    currentScreen = Screen.HOME
                },
                onEmailSignInClick = {
                    currentScreen = Screen.LOGIN_EMAIL
                }
            )
        }
        Screen.LOGIN_EMAIL -> {
            EmailLoginScreen(
                onBackClick = {
                    currentScreen = Screen.LOGIN_PHONE
                },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { email ->
                    currentScreen = Screen.OTP_EMAIL
                },
                onGoogleSignInClick = {
                    currentScreen = Screen.HOME
                },
                onPhoneSignInClick = {
                    currentScreen = Screen.LOGIN_PHONE
                }
            )
        }
        Screen.OTP_PHONE -> {
            OtpScreen(
                otpType = OtpType.PHONE,
                onBackClick = {
                    currentScreen = Screen.LOGIN_PHONE
                },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { otpCode ->
                    currentScreen = Screen.HOME
                }
            )
        }
        Screen.OTP_EMAIL -> {
            OtpScreen(
                otpType = OtpType.EMAIL,
                onBackClick = {
                    currentScreen = Screen.LOGIN_EMAIL
                },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { otpCode ->
                    currentScreen = Screen.HOME
                }
            )
        }
        Screen.HOME -> {
            HomeScreen(
                onSearchClick = {
                    currentScreen = Screen.SEARCH
                },
                onResortClick = {
                    currentScreen = Screen.RESORT_DETAIL
                }
            )
        }
        Screen.SEARCH -> {
            SearchScreen(
                onBackClick = {
                    currentScreen = Screen.HOME
                },
                onResortClick = {
                    currentScreen = Screen.RESORT_DETAIL
                }
            )
        }
        Screen.RESORT_DETAIL -> {
            ResortDetailScreen(
                onBackClick = {
                    currentScreen = Screen.HOME
                },
                onCallClick = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+62361701888"))
                    context.startActivity(intent)
                },
                onBookClick = {
                    // Handle booking action
                }
            )
        }
    }
}
