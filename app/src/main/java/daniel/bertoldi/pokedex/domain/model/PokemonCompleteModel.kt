package daniel.bertoldi.pokedex.domain.model

data class PokemonCompleteModel(
    val id: Int,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val name: String,
    val sprites: SpritesModel,
    val types: List<Types>,
    val weight: Int,
    val abilities: List<AbilityModel>,
    val stats: StatsModel,
    val species: SpeciesModel,
    val typeEffectiveness: TypeEffectivenessModel,
)

data class AbilityModel(
    val id: Int,
    val name: String,
    val isHidden: Boolean,
    val slot: Int,
    val effectEntry: String,
    val shortEffectEntry: String,
    val flavorText: String,
    val generation: String,
    val isMainSeries: Boolean,
)

data class StatsModel(
    val hp: Int? = null,
    val attack: Int? = null,
    val defense: Int? = null,
    val specialAttack: Int? = null,
    val specialDefense: Int? = null,
    val speed: Int? = null,
    val accuracy: Int? = null,
    val evasion: Int? = null,
)

data class SpeciesModel(
    val baseHappiness: Int,
    val captureRate: Int,
    val eggGroups: List<String>,
    val genderRate: Int,
    val pokedexEntry: String,
    val growthRate: String,
    val isBaby: Boolean,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val hatchCounter: Int,
    val name: String,
)

data class TypeEffectivenessModel(
    val normal: Float?,
    val fighting: Float?,
    val flying: Float?,
    val poison: Float?,
    val ground: Float?,
    val rock: Float?,
    val bug: Float?,
    val ghost: Float?,
    val steel: Float?,
    val fire: Float?,
    val water: Float?,
    val grass: Float?,
    val electric: Float?,
    val psychic: Float?,
    val ice: Float?,
    val dragon: Float?,
    val dark: Float?,
    val fairy: Float?,
    val unknown: Float?,
    val shadow: Float?,
)