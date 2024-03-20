package daniel.bertoldi.pokedex.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Delete this after all colors have been added.
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

/* Pokémon Type Colors */
val TypeBug = Color(0xFF8CB230)
val TypeDark = Color(0xFF58575F)
val TypeDragon = Color(0xFF0F6AC0)
val TypeElectric = Color(0xFFEED535)
val TypeFairy = Color(0xFFED6EC7)
val TypeFighting = Color(0xFFD04164)
val TypeFire = Color(0xFFFD7D24)
val TypeFlying = Color(0xFF748FC9)
val TypeGhost = Color(0xFF556AAE)
val TypeGrass = Color(0xFF62B957)
val TypeGround = Color(0xFFDD7748)
val TypeIce = Color(0xFF61CEC0)
val TypeNormal = Color(0xFF61CEC0)
val TypePoison = Color(0xFFA552CC)
val TypePsychic = Color(0xFFEA5D60)
val TypeRock = Color(0xFFBAAB82)
val TypeSteel = Color(0xFF417D9A)
val TypeWater = Color(0xFF4A90DA)

/* Pokémon Background Type Colors */
val BgTypeBug = Color(0xFF8BD674)
val BgTypeDark = Color(0xFF6F6E78)
val BgTypeDragon = Color(0xFF7383B9)
val BgTypeElectric = Color(0xFFF2CB55)
val BgTypeFairy = Color(0xFFEBA8C3)
val BgTypeFighting = Color(0xFFEB4971)
val BgTypeFire = Color(0xFFFFA756)
val BgTypeFlying = Color(0xFF83A2E3)
val BgTypeGhost = Color(0xFF8571BE)
val BgTypeGrass = Color(0xFF8BBE8A)
val BgTypeGround = Color(0xFFF78551)
val BgTypeIce = Color(0xFF91D8DF)
val BgTypeNormal = Color(0xFFB5B9C4)
val BgTypePoison = Color(0xFF9F6E97)
val BgTypePsychic = Color(0xFFFF6568)
val BgTypeRock = Color(0xFFD4C294)
val BgTypeSteel = Color(0xFF4C91B2)
val BgTypeWater = Color(0xFF58ABF6)

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