package daniel.bertoldi.pokedex.domain.model

data class PokemonModel(
    val abilities: List<Abilities>,
    val height: Int,
    val id: Int,
    val isDefault: Boolean,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stats>,
    val types: List<Types>,
    val weight: Int,
)

data class Abilities(
    val ability: Ability,
    val isHidden: Boolean,
    val slot: Int,
)

data class Ability(
    val name: String,
    val url: String,
)

data class Sprites(
    val backDefault: String,
    val backShiny: String,
    val frontDefault: String,
    val frontShiny: String,
    val artwork: String,
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