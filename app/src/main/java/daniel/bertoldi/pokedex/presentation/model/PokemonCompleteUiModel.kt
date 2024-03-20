package daniel.bertoldi.pokedex.presentation.model

data class PokemonCompleteUiModel(
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
