package daniel.bertoldi.pokedex.ui.theme

import androidx.compose.ui.graphics.Color
import daniel.bertoldi.pokedex.R

// Delete this after all colors have been added.
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


/* Pokémon UI Data by Type */
enum class PokemonUIData(val typeColor: Color, val backgroundColor: Color, val icon: Int) {
    BUG(Color(0xFF8CB230), Color(0xFF8BD674), R.drawable.ic_bug_type),
    DARK(Color(0xFF58575F), Color(0xFF6F6E78), R.drawable.ic_dark_type),
    DRAGON(Color(0xFF0F6AC0), Color(0xFF7383B9), R.drawable.ic_dragon_type),
    ELECTRIC(Color(0xFFEED535), Color(0xFFF2CB55), R.drawable.ic_electric_type),
    FAIRY(Color(0xFFED6EC7), Color(0xFFEBA8C3), R.drawable.ic_fairy_type),
    FIGHTING(Color(0xFFD04164), Color(0xFFEB4971), R.drawable.ic_fighting_type),
    FIRE(Color(0xFFFD7D24), Color(0xFFFFA756), R.drawable.ic_fire_type),
    FLYING(Color(0xFF748FC9), Color(0xFF83A2E3), R.drawable.ic_flying_type),
    GHOST(Color(0xFF556AAE), Color(0xFF8571BE), R.drawable.ic_ghost_type),
    GRASS(Color(0xFF62B957), Color(0xFF8BBE8A), R.drawable.ic_grass_type),
    GROUND(Color(0xFFDD7748), Color(0xFFF78551), R.drawable.ic_ground_type),
    ICE(Color(0xFF61CEC0), Color(0xFF91D8DF), R.drawable.ic_ice_type),
    NORMAL(Color(0xFF9DA0AA), Color(0xFFB5B9C4), R.drawable.ic_normal_type),
    POISON(Color(0xFFA552CC), Color(0xFF9F6E97), R.drawable.ic_poison_type),
    PSYCHIC(Color(0xFFEA5D60), Color(0xFFFF6568), R.drawable.ic_psychic_type),
    ROCK(Color(0xFFBAAB82), Color(0xFFD4C294), R.drawable.ic_rock_type),
    STEEL(Color(0xFF417D9A), Color(0xFF4C91B2), R.drawable.ic_steel_type),
    WATER(Color(0xFF4A90DA), Color(0xFF58ABF6), R.drawable.ic_water_type);

    companion object {
        fun findTypeUiData(name: String) = values().firstOrNull { it.name == name }
    }
}

/* Generic Background Colors */
val BgWhite = Color(0xFFFFFFFF)
val BgDefaultInput = Color(0xFFF2F2F2)
val BgPressedInput = Color(0xFFE2E2E2)
val BgModal = Color(0x8017171B)
val LightGrey = Color(0xFFCDCDCD)
val GradientGrey = Color(0xFFE5E5E5)
val GradientLightGrey = Color(0xFFECECEC)
val GradientLightWhite = Color(0xFFF5F5F5)

/* Text Colors */
val TextWhite = Color(0xFFFFFFFF)
val TextBlack = Color(0xFF17171B)
val TextGrey = Color(0xFF747476)
val TextNumber = Color(0x9917171B)

/* Height Colors */
enum class HeightUIData(
    val heightLimit: Int,
    val color: Color,
    val iconUnselected: Int,
) {
    SHORT(
        heightLimit = 10,
        color = Color(0xFFFFC5E6),
        iconUnselected = R.drawable.ic_height_short,
    ),
    MEDIUM(
        heightLimit = 100,
        color = Color(0xFFAEBFD7),
        iconUnselected = R.drawable.ic_height_medium,
    ),
    TALL(
        heightLimit = 1000,
        color = Color(0xFFAAACB8),
        iconUnselected = R.drawable.ic_height_tall,
    ),
}

/* Weight Colors */
enum class WeightUIData(
    val weightLimit: Int,
    val color: Color,
    val iconUnselected: Int,
) {
    LIGHT(
        weightLimit = 10,
        color = Color(0xFF99CD7C),
        iconUnselected = R.drawable.ic_weight_light,
    ),
    NORMAL(
        weightLimit = 100,
        color = Color(0xFF57B2DC),
        iconUnselected = R.drawable.ic_weight_normal,
    ),
    HEAVY(
        weightLimit = 1000,
        color = Color(0xFF5A92A5),
        iconUnselected = R.drawable.ic_weight_heavy,
    ),
}