package hu.bme.aut.android.gaminggoods.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.gaminggoods.R

val myFont = FontFamily(
    Font(R.font.ubuntu_condensed_regular, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = myFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    ),

    bodyMedium = TextStyle(
        fontFamily = myFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    ),

    titleMedium = TextStyle(
        fontFamily = myFont,
        fontSize = 18.sp,
        lineHeight = 22.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = myFont,
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.5.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    ),
)