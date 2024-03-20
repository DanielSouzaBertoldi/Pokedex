package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json

data class PokemonSpeciesResponse(
    val id: Int,
    @Json(name = "base_happiness") val baseHappiness: Int,
    @Json(name = "capture_rate") val captureRate: Int,
    @Json(name = "egg_groups") val eggGroups: List<GenericObject>,
    @Json(name = "gender_rate") val genderRate: Int,
    @Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntries>,
    @Json(name = "growth_rate") val growthRate: GenericObject,
    @Json(name = "is_baby") val isBaby: Boolean,
    @Json(name = "is_legendary") val isLegendary: Boolean,
    @Json(name = "is_mythical") val isMythical: Boolean,
    @Json(name = "hatch_counter") val hatchCounter: Int,
)
