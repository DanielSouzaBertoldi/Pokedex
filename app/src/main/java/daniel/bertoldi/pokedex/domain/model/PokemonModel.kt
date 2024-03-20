package daniel.bertoldi.pokedex.domain.model

data class PokemonModel(
    val abilities: List<Ability>,
    val height: Int,
    val id: Int,
    val isDefault: Boolean,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stats>,
    val types: List<Types>,
    val weight: Int,
)

data class Ability(
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

data class Sprites(
    val backDefaultImageUrl: String?,
    val backShinyImageUrl: String?,
    val frontDefaultImageUrl: String?,
    val frontShinyImageUrl: String?,
    val artworkImageUrl: String,
)

data class Stats(
    val baseStat: Int,
    val effort: Int,
    val stat: Stat,
)

data class Stat(
    val name: String,
    val url: String,
)

data class Types(
    val slot: Int,
    val type: Type,
)

data class Type(
    val name: String,
    val url: String,
)
