package daniel.bertoldi.pokedex.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import daniel.bertoldi.pokedex.R

val pokemonFont = FontFamily(
    Font(R.font.sf_pro_display_bold, weight = FontWeight.Bold),
    Font(R.font.sf_pro_display_medium, weight = FontWeight.Medium),
    Font(R.font.sf_pro_display_regular, weight = FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontSize = 100.sp,
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Bold,
    ),
    h2 = TextStyle(
        fontSize = 32.sp,
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Bold,
    ),
    h3 = TextStyle(
        fontSize = 26.sp,
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Bold,
    ),
    h4 = TextStyle(
        fontSize = 16.sp,
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Bold,
    ),
    body1 = TextStyle(
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontSize = 12.sp,
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Bold,
    ),
    subtitle2 = TextStyle(
        fontSize = 12.sp,
        fontFamily = pokemonFont,
        fontWeight = FontWeight.Medium,
    ),
)