package com.traveo.app.feature.auth.presentation.welcome

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.traveo.app.R
import com.traveo.app.core.designsystem.theme.TraveoTheme

@Composable
fun WelcomeScreen(
    onSignInClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
    onAppleSignInClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Traveo Logo",
                    modifier = Modifier.size(36.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_welcome_hero),
                    contentDescription = "Welcome Hero Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            // Headline & Subtitle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Start ",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    // Slanted 3-Photo Pill Collage
                    PillCollage(
                        leftImageRes = R.drawable.pill_left,
                        topRightImageRes = R.drawable.pill_top_right,
                        bottomRightImageRes = R.drawable.pill_bottom_right,
                        modifier = Modifier
                            .height(30.dp)
                            .width(58.dp)
                    )

                    Text(
                        text = " Your Next",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = "Premium Travel",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Experience Here",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "By continuing I agree with the\nPrivacy Policy and Terms & Conditions",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            // Action Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp, top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onSignInClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "I have an account",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_up_right),
                            contentDescription = "Arrow",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                SocialAuthButton(
                    iconRes = R.drawable.ic_google,
                    text = "Continue with Google",
                    tint = Color.Unspecified,
                    onClick = onGoogleSignInClick
                )

//                SocialAuthButton(
//                    iconRes = R.drawable.ic_apple,
//                    text = "Continue with Apple",
//                    tint = MaterialTheme.colorScheme.onSurface,
//                    onClick = onAppleSignInClick
//                )
            }
        }
    }
}

/**
 * Slanted 3-Photo Pill Collage:
 * Draws a pill shape with a slanted vertical divider (left photo) and a slanted horizontal divider
 * (top-right vs bottom-right photo), matching the exact design layout.
 */
@Composable
fun PillCollage(
    leftImageRes: Int,
    topRightImageRes: Int,
    bottomRightImageRes: Int,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Black,
    dividerWidthDp: Float = 2.5f
) {
    val imgLeft = ImageBitmap.imageResource(id = leftImageRes)
    val imgTopRight = ImageBitmap.imageResource(id = topRightImageRes)
    val imgBottomRight = ImageBitmap.imageResource(id = bottomRightImageRes)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(borderColor)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val strokePx = dividerWidthDp.dp.toPx()

            // Left Polygon Path (Slanted Right Edge)
            val pathLeft = Path().apply {
                moveTo(0f, 0f)
                lineTo(w * 0.58f, 0f)
                lineTo(w * 0.42f, h)
                lineTo(0f, h)
                close()
            }

            // Top-Right Polygon Path (Slanted Bottom Edge)
            val pathTopRight = Path().apply {
                moveTo(w * 0.58f, 0f)
                lineTo(w, 0f)
                lineTo(w, h * 0.54f)
                lineTo(w * 0.48f, h * 0.36f)
                close()
            }

            // Bottom-Right Polygon Path
            val pathBottomRight = Path().apply {
                moveTo(w * 0.48f, h * 0.36f)
                lineTo(w, h * 0.54f)
                lineTo(w, h)
                lineTo(w * 0.42f, h)
                close()
            }

            // Draw Left Image
            clipPath(pathLeft) {
                drawImage(
                    image = imgLeft,
                    dstSize = IntSize(w.toInt(), h.toInt())
                )
            }

            // Draw Top-Right Image
            clipPath(pathTopRight) {
                drawImage(
                    image = imgTopRight,
                    dstSize = IntSize(w.toInt(), h.toInt())
                )
            }

            // Draw Bottom-Right Image
            clipPath(pathBottomRight) {
                drawImage(
                    image = imgBottomRight,
                    dstSize = IntSize(w.toInt(), h.toInt())
                )
            }

            // Slanted Vertical Divider Line
            drawLine(
                color = borderColor,
                start = Offset(w * 0.58f, 0f),
                end = Offset(w * 0.42f, h),
                strokeWidth = strokePx
            )

            // Slanted Horizontal Divider Line
            drawLine(
                color = borderColor,
                start = Offset(w * 0.48f, h * 0.36f),
                end = Offset(w, h * 0.54f),
                strokeWidth = strokePx
            )

            // Thick Black Outer Border
            drawRoundRect(
                color = borderColor,
                size = size,
                cornerRadius = CornerRadius(h / 2, h / 2),
                style = Stroke(width = strokePx * 1.5f)
            )
        }
    }
}

@Composable
private fun SocialAuthButton(
    iconRes: Int,
    text: String,
    tint: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    TraveoTheme {
        WelcomeScreen()
    }
}
