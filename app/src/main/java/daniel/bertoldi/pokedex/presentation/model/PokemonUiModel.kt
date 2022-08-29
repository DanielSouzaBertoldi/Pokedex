package daniel.bertoldi.pokedex.presentation.model

data class PokemonUiModel(
    val abilities: List<UiAbilities>,
    val height: Int,
    val id: Int,
    val isDefault: Boolean,
    val name: String,
    val uiSprites: UiSprites,
    val stats: List<UiStats>,
    val types: List<UiTypes>,
    val weight: Int,
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
    val uiStat: UiStat,
)

data class UiStat(
    val name: String,
    val url: String,
)

data class UiTypes(
    val slot: Int,
    val uiType: UiType,
)

data class UiType(
    val name: String,
    val url: String,
)
