package com.artemObrazumov.drinkin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.artemObrazumov.drinkin.R

val Causten = FontFamily(
    Font(
        resId = R.font.causten_thin,
        weight = FontWeight.Thin,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_thin_oblique,
        weight = FontWeight.Thin,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_extra_light,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_extra_light_oblique,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_light,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_light_oblique,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_regular_oblique,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_medium_oblique,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_semi_bold_oblique,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_bold_oblique,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_extra_bold,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_extra_bold_oblique,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.causten_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.causten_black_oblique,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Causten
    )
)