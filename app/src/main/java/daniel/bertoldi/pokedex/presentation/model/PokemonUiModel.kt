package daniel.bertoldi.pokedex.presentation.model

import androidx.compose.ui.graphics.Color

data class PokemonUiModel(
    val abilities: List<UiAbilities>,
    val height: Int,
    val id: Int,
    val pokedexNumber: String,
    val isDefault: Boolean,
    val name: String,
    val uiSprites: UiSprites,
    val stats: List<UiStats>,
    val types: List<UiType>,
    val weight: Int,
    val backgroundColors: BackgroundColors,
)

data class UiAbilities(
    val uiAbility: UiAbility,
    val isHidden: Boolean,
    val slot: Int,
)

data class UiAbility(
    val name: String,
    val url: String,
)

data class UiSprites(
    val backDefault: String,
    val backShiny: String,
    val frontDefault: String,
    val frontShiny: String,
    val artwork: String,
)

data class UiStats(
    val baseStat: Int,
    val effort: Int,
    val name: String,
    val url: String,
)

data class UiType(
    val slot: Int,
    val name: String,
    val url: String,
    val backgroundColor: Color,
    val icon: Int,
)

data class BackgroundColors(
    val typeColor: Color,
    val backgroundTypeColor: Color,
)
