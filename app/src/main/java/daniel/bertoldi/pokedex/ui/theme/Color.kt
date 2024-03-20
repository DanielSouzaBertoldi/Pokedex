package daniel.bertoldi.pokedex.ui.theme

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import daniel.bertoldi.pokedex.R

// Delete this after all colors have been added.
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


/* Pokémon UI Data by Type */
enum class PokemonUIData(val typeColor: Color, val backgroundColor: Color, val icon: Int) {
    BUG(Color(0xFF8CB230), Color(0xFF8BD674), R.drawable.ic_grass_type),
    DARK(Color(0xFF58575F), Color(0xFF6F6E78), R.drawable.ic_grass_type),
    DRAGON(Color(0xFF0F6AC0), Color(0xFF7383B9), R.drawable.ic_grass_type),
    ELECTRIC(Color(0xFFEED535), Color(0xFFF2CB55), R.drawable.ic_grass_type),
    FAIRY(Color(0xFFED6EC7), Color(0xFFEBA8C3), R.drawable.ic_grass_type),
    FIGHTING(Color(0xFFD04164), Color(0xFFEB4971), R.drawable.ic_grass_type),
    FIRE(Color(0xFFFD7D24), Color(0xFFFFA756), R.drawable.ic_grass_type),
    FLYING(Color(0xFF748FC9), Color(0xFF83A2E3), R.drawable.ic_grass_type),
    GHOST(Color(0xFF556AAE), Color(0xFF8571BE), R.drawable.ic_grass_type),
    GRASS(Color(0xFF62B957), Color(0xFF8BBE8A), R.drawable.ic_grass_type),
    GROUND(Color(0xFFDD7748), Color(0xFFF78551), R.drawable.ic_grass_type),
    ICE(Color(0xFF61CEC0), Color(0xFF91D8DF), R.drawable.ic_grass_type),
    NORMAL(Color(0xFF9DA0AA), Color(0xFFB5B9C4), R.drawable.ic_grass_type),
    POISON(Color(0xFFA552CC), Color(0xFF9F6E97), R.drawable.ic_poison_type),
    PSYCHIC(Color(0xFFEA5D60), Color(0xFFFF6568), R.drawable.ic_grass_type),
    ROCK(Color(0xFFBAAB82), Color(0xFFD4C294), R.drawable.ic_grass_type),
    STEEL(Color(0xFF417D9A), Color(0xFF4C91B2), R.drawable.ic_grass_type),
    WATER(Color(0xFF4A90DA), Color(0xFF58ABF6), R.drawable.ic_grass_type),
}

/* Generic Background Colors */
val BgWhite = Color(0xFFFFFFFF)
val BgDefaultInput = Color(0xFFF2F2F2)
val BgPressedInput = Color(0xFFE2E2E2)
val BgModal = Color(0x8017171B)

/* Gradient Colors */
val GradientVector = Brush.verticalGradient(
    colors = listOf(
        Color(0x4DFFFFFF),
        Color(0x00FFFFFF),
    )
)
val GradientPokeball = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF5F5F5),
        Color(0xFFFFFFFF),
    ),
    // this is probably wrong. I should calculate the density of the screen to
    // calculate the offset properly. Check:
    // https://stackoverflow.com/questions/71702233/how-to-create-angular-gradient-in-jetpack-compose
    startY = 50f,
)
val GradientVectorGrey = Brush.linearGradient(
    colors = listOf(
        Color(0xFFE5E5E5),
        Color(0x00F5F5F5),
    ),
    // Why these values? See here:
    // https://stackoverflow.com/questions/67598613/android-compose-custom-lineargradient-with-angle-like-gradientdrawable
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY),
    // This angle basically goes from Top Left to Bottom Right of the view.
)
val GradientPokeballGrey = Brush.linearGradient(
    colors = listOf(
        Color(0xFFECECEC),
        Color(0xFFF5F5F5),
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY),
)
val GradientVectorWhite = Brush.linearGradient(
    colors = listOf(
        Color(0x4DFFFFFF),
        Color(0x00FFFFFF),
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY),
)
val GradientPokeballWhite = Brush.linearGradient(
    colors = listOf(
        Color(0x1AFFFFFF),
        Color(0x00FFFFFF),
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY),
)
val GradientPokemonName = Brush.verticalGradient(
    colors = listOf(
        Color(0x4DFFFFFF),
        Color(0x00FFFFFF),
    )
)
val GradientPokemonCircle = Brush.linearGradient(
    colors = listOf(
        Color(0x00FFFFFF),
        Color(0x59FFFFFF),
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY),
)

/* Text Colors */
val TextWhite = Color(0xFFFFFFFF)
val TextBlack = Color(0xFF17171B)
val TextGrey = Color(0xFF747476)
val TextNumber = Color(0x9917171B)

/* Height Colors */
val HeightShort = Color(0xFFFFC5E6)
val HeightMedium = Color(0xFFAEBFD7)
val HeightTall = Color(0xFFAAACB8)

/* Weight Colors */
val WeightLight = Color(0xFF99CD7C)
val WeightNormal = Color(0xFF57B2DC)
val WeightHeavy = Color(0xFF5A92A5)
