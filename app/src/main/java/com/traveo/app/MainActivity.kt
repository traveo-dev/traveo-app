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
import com.traveo.app.feature.auth.presentation.login.LoginScreen
import com.traveo.app.feature.auth.presentation.welcome.WelcomeScreen

private enum class Screen {
    WELCOME,
    LOGIN
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
                    currentScreen = Screen.LOGIN
                },
                onGoogleSignInClick = {

                }
            )
        }
        Screen.LOGIN -> {
            LoginScreen(
                onBackClick = {
                    currentScreen = Screen.WELCOME
                },
                onInfoClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://traveo.com"))
                    context.startActivity(intent)
                },
                onContinueClick = { dialCode, phoneNumber ->

                },
                onGoogleSignInClick = {

                },
                onEmailSignInClick = {

                }
            )
        }
    }
}
