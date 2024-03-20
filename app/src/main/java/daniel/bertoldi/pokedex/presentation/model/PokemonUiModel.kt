package daniel.bertoldi.pokedex.presentation.model

import androidx.compose.ui.graphics.Color

data class PokemonUiModel(
    val abilities: List<PokemonUiAbility>,
    val height: Int,
    val id: Int,
    val pokedexNumber: String,
    val isDefault: Boolean,
    val name: String,
    val uiSprites: UiSprites,
    val types: List<UiType>,
    val weight: Int,
    val backgroundColors: BackgroundColors,
)

data class PokemonUiAbility(
    val name: String,
    val isHidden: Boolean,
    val slot: Int,
    val effectEntry: String,
    val shortEffectEntry: String,
    val flavorText: String,
    val generation: String,
    val isMainSeries: Boolean,
)

data class UiSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?,
    val artwork: String,
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
