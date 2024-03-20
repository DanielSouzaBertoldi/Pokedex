package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    val abilities: List<PokemonAbilitiesResponse>,
    @Json(name = "base_experience") val baseExperience: Int?,
    val height: Int,
    val id: Int,
    @Json(name = "is_default") val isDefault: Boolean,
    val name: String,
    val sprites: SpritesResponse,
    val stats: List<StatsResponse>,
    val types: List<TypesResponse>,
    val weight: Int,
    val species: GenericObject,
)

@JsonClass(generateAdapter = true)
data class PokemonAbilitiesResponse(
    val ability: PokemonAbilityResponse,
    @Json(name = "is_hidden") val isHidden: Boolean,
    val slot: Int,
)

@JsonClass(generateAdapter = true)
data class PokemonAbilityResponse(
    val name: String,
    val url: String,
)

@JsonClass(generateAdapter = true)
data class SpritesResponse(
    @Json(name = "back_default") val backDefault: String?,
    @Json(name = "back_female") val backFemale: String?,
    @Json(name = "back_shiny") val backShiny: String?,
    @Json(name = "back_shiny_female") val backShinyFemale: String?,
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_female") val frontFemale: String?,
    @Json(name = "front_shiny") val frontShiny: String?,
    @Json(name = "front_shiny_female") val frontShinyFemale: String?,
    @Json(name = "other") val otherSprites: OtherSpritesResponse,
)

@JsonClass(generateAdapter = true)
data class OtherSpritesResponse(
    @Json(name = "dream_world") val dreamWorldSprites: DreamWorldSprites,
    @Json(name = "home") val homeSprites: HomeSprites,
    @Json(name = "official-artwork") val officialArtworkSprites: OfficialArtworkSprites,
)

@JsonClass(generateAdapter = true)
data class DreamWorldSprites(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_female") val frontFemale: String?,
)

@JsonClass(generateAdapter = true)
data class HomeSprites(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_female") val frontFemale: String?,
    @Json(name = "front_shiny") val frontShiny: String?,
    @Json(name = "front_shiny_female") val frontShinyFemale: String?,
)

@JsonClass(generateAdapter = true)
data class OfficialArtworkSprites(
    @Json(name = "front_default") val frontDefault: String?,
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
