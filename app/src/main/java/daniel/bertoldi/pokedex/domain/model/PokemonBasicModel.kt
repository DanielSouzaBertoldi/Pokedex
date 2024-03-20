package daniel.bertoldi.pokedex.domain.model

data class PokemonBasicModel(
    val height: Int,
    val id: Int,
    val isDefault: Boolean,
    val name: String,
    val sprites: Sprites,
    val types: List<Types>,
    val weight: Int,
)

data class Sprites(
    val backDefaultImageUrl: String?,
    val backShinyImageUrl: String?,
    val frontDefaultImageUrl: String?,
    val frontShinyImageUrl: String?,
    val artworkImageUrl: String,
)

data class Types(
    val slot: Int,
    val type: Type,
)

data class Type(
    val name: String,
    val url: String,
)
