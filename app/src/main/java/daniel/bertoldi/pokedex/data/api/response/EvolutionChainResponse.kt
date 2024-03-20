package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EvolutionChainResponse(
    val chain: ChainLinkResponse,
)

@JsonClass(generateAdapter = true)
data class ChainLinkResponse(
    @Json(name = "is_baby") val isBaby: Boolean,
    val species: GenericObject,
    @Json(name = "evolution_details") val evolutionDetails: List<EvolutionDetailsResponse>,
    @Json(name = "evolves_to") val chain: List<ChainLinkResponse>,
)

@JsonClass(generateAdapter = true)
data class EvolutionDetailsResponse(
    val item: GenericObject?,
    val trigger: GenericObject?,
    val gender: Int?,
    @Json(name = "held_item") val heldItem: GenericObject?,
    @Json(name = "known_move") val knownMove: GenericObject?,
    @Json(name = "known_move_type") val knownMoveType: GenericObject?,
    val location: GenericObject?,
    @Json(name = "min_level") val minLevel: Int?,
    @Json(name = "min_happiness") val minHappiness: Int?,
    @Json(name = "min_beauty") val minBeauty: Int?,
    @Json(name = "min_affection") val minAffection: Int?,
    @Json(name = "needs_overworld_rain") val needsOverworldRain: Boolean,
    @Json(name = "party_species") val partySpecies: GenericObject?,
    @Json(name = "party_type") val partyType: GenericObject?,
    @Json(name = "relative_physical_stats") val relativePhysicalStats: Int?,
    @Json(name = "time_of_day") val timeOfDay: String,
    @Json(name = "turn_upside_down") val turnUpsideDown: Boolean,
)