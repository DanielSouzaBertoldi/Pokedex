package daniel.bertoldi.pokedex.presentation.model

import androidx.compose.ui.text.AnnotatedString

data class PokemonCompleteUiModel(
    val id: Int,
    val height: DuoTextUi,
    val pokedexNumber: String,
    val isDefault: Boolean,
    val name: String,
    val uiSprites: UiSprites,
    val types: List<UiType>,
    val weight: DuoTextUi,
    val backgroundColors: BackgroundColors,
    val baseExperience: Int,
    val abilities: List<PokemonUiAbility>,
    val stats: StatsUiModel,
    val species: SpeciesUiModel,
    val typeEffectiveness: TypeEffectivenessUiModel,
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

data class StatsUiModel(
    val hp: StatDetailUiModel?,
    val attack: StatDetailUiModel?,
    val defense: StatDetailUiModel?,
    val specialAttack: StatDetailUiModel?,
    val specialDefense: StatDetailUiModel?,
    val speed: StatDetailUiModel?,
    val accuracy: StatDetailUiModel?,
    val evasion: StatDetailUiModel?,
)

data class StatDetailUiModel(
    val baseStat: Int?,
    val minMaxStat: Int?, // cool name!
    val maxStat: Int?,
)

data class SpeciesUiModel(
    val catchRate: DuoTextUi,
    val genderRate: AnnotatedString,
    val pokedexEntry: String,
    val growthRate: String,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val eggCycles: DuoTextUi,
    val name: String,
)

data class DuoTextUi(
    val mainText: String,
    val secondaryText: String,
)

data class TypeEffectivenessUiModel(
    val normal: Effectiveness?,
    val fighting: Effectiveness?,
    val flying: Effectiveness?,
    val poison: Effectiveness?,
    val ground: Effectiveness?,
    val rock: Effectiveness?,
    val bug: Effectiveness?,
    val ghost: Effectiveness?,
    val steel: Effectiveness?,
    val fire: Effectiveness?,
    val water: Effectiveness?,
    val grass: Effectiveness?,
    val electric: Effectiveness?,
    val psychic: Effectiveness?,
    val ice: Effectiveness?,
    val dragon: Effectiveness?,
    val dark: Effectiveness?,
    val fairy: Effectiveness?,
    val unknown: Effectiveness?,
    val shadow: Effectiveness?,
)

enum class Effectiveness {
    NONE,
    WEAK,
    NORMAL,
    KINDA_STRONG,
    STRONG
}
