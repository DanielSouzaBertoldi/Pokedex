package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    val abilities: List<AbilitiesResponse>,
    val height: Int,
    val id: Int,
    @Json(name = "is_default") val isDefault: Boolean,
    val name: String,
    val sprites: SpritesResponse,
    val stats: List<StatsResponse>,
    val types: List<TypesResponse>,
    val weight: Int,
)

@JsonClass(generateAdapter = true)
data class AbilitiesResponse(
    val ability: AbilityResponse,
    @Json(name = "is_hidden") val isHidden: Boolean,
    val slot: Int,
)

@JsonClass(generateAdapter = true)
data class AbilityResponse(
    val name: String,
    val url: String,
)

@JsonClass(generateAdapter = true)
data class SpritesResponse(
    @Json(name = "back_default") val backDefault: String,
    @Json(name = "back_shiny") val backShiny: String,
    @Json(name = "front_default") val frontDefault: String,
    @Json(name = "front_shiny") val frontShiny: String,
)

@JsonClass(generateAdapter = true)
data class StatsResponse(
    @Json(name = "base_stat") val baseStat: Int,
    val effort: Int,
    val stat: StatResponse,
)

@JsonClass(generateAdapter = true)
data class StatResponse(
    val name: String,
    val url: String,
)

@JsonClass(generateAdapter = true)
data class TypesResponse(
    val slot: Int,
    val type: TypeResponse,
)

@JsonClass(generateAdapter = true)
data class TypeResponse(
    val name: String,
    val url: String,
)
