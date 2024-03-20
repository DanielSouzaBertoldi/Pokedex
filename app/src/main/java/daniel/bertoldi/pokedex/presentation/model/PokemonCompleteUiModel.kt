package daniel.bertoldi.pokedex.presentation.model

import androidx.compose.ui.text.AnnotatedString

data class PokemonCompleteUiModel(
    val id: Int,
    val height: AnnotatedString,
    val pokedexNumber: String,
    val pokedexEntry: String,
    val isDefault: Boolean,
    val name: String,
    val uiSprites: UiSprites,
    val types: List<UiType>,
    val weight: AnnotatedString,
    val backgroundColors: BackgroundColors,
    val baseExperience: Int,
    val abilities: List<PokemonUiAbility>,
    val stats: StatsUiModel,
    val species: SpeciesUiModel,
    val typeEffectiveness: TypeEffectivenessUiModel,
    val evolutionChain: EvolutionChainUiModel,
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
    val total: String,
)

data class StatDetailUiModel(
    val baseStat: String,
    val baseStatFloat: Float,
    val minMaxStat: String, // cool name!
    val maxStat: String,
)

data class SpeciesUiModel(
    val catchRate: AnnotatedString,
    val genderRate: AnnotatedString,
    val pokedexEntry: String,
    val growthRate: String,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val eggCycles: AnnotatedString,
    val eggGroups: String,
    val name: String,
)

data class TypeEffectivenessUiModel( // talvez seja melhor uma lista de TypeEffectiveness...
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
) {
    operator fun iterator(): Iterator<Pair<String, Effectiveness?>> = listOf(
        "NORMAL" to normal,
        "FIGHTING" to fighting,
        "FLYING" to flying,
        "POISON" to poison,
        "GROUND" to ground,
        "ROCK" to rock,
        "BUG" to bug,
        "GHOST" to ghost,
        "STEEL" to steel,
        "FIRE" to fire,
        "WATER" to water,
        "GRASS" to grass,
        "ELECTRIC" to electric,
        "PSYCHIC" to psychic,
        "ICE" to ice,
        "DRAGON" to dragon,
        "DARK" to dark,
        "FAIRY" to fairy,
        "UNKOWN" to unknown,
        "SHADOW" to shadow,
    ).iterator()

    fun getATypeEffectiveness(expectedEffectiveness: Effectiveness): List<String> {
        val listOfEffectiveness = mutableListOf<String>()
        for ((key, value) in this) {
            if (value == expectedEffectiveness) {
                listOfEffectiveness.add(key)
            }
        }
        return listOfEffectiveness
    }

    fun getTypeEffectivenessByName(name: String): Effectiveness? {
        for ((key, value) in this) {
            if (key == name) {
                return value
            }
        }
        return null
    }
}

enum class Effectiveness {
    NONE,
    WEAK,
    NORMAL,
    KINDA_STRONG,
    STRONG
}

data class EvolutionChainUiModel(
    val name: String,
    val pokedexNumber: String,
    val imageUrl: String,
    val isBaby: Boolean,
    val heldItem: String? = null,
    val knownMove: String? = null,
    val knownMoveType: String? = null,
    val minLevel: Int? = null,
    val minHappiness: Int? = null,
    val minBeauty: Int? = null,
    val minAffection: Int? = null,
    val needsOverworldRain: Boolean,
    val partySpecies: String? = null,
    val partyType: String? = null,
    val relativePhysicalStats: Int? = null,
    val timeOfDay: String,
    val turnUpsideDown: Boolean,
    val location: String? = null,
    val trigger: String? = null,
    val item: String? = null,
    val gender: Int? = null,
    val nextEvolutions: List<EvolutionChainUiModel>,
)
