package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json

data class AbilityResponse(
    val id: Int,
    val name: String,
    @Json(name = "effect_entries")
    val effectEntries: List<EffectEntries>,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntries>,
    val generation: GenericObject,
    @Json(name = "is_main_series")
    val isMainSeries: Boolean,
    val pokemon: List<Pokemon>
)

data class EffectEntries(
    val effect: String,
    val language: GenericObject,
    @Json(name = "short_effect")
    val shortEffect: String,
)

data class GenericObject(
    val name: String,
    val url: String,
)

data class FlavorTextEntries(
    @Json(name = "flavor_text")
    val flavorText: String,
    val language: GenericObject,
    @Json(name = "version_group")
    val versionGroup: GenericObject?,
)

data class Pokemon(
    @Json(name = "is_hidden")
    val isHidden: Boolean,
    @Json(name = "pokemon")
    val data: GenericObject,
    val slot: Int,
)
