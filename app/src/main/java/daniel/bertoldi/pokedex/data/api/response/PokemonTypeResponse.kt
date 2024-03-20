package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonTypeResponse(
    @Json(name = "damage_relations") val damageRelations: DamageRelations,
)

@JsonClass(generateAdapter = true)
data class DamageRelations(
    @Json(name = "double_damage_from") val defenseDouble: List<GenericObject>,
    @Json(name = "double_damage_to") val attackDouble: List<GenericObject>,
    @Json(name = "half_damage_from") val defenseHalf: List<GenericObject>,
    @Json(name = "half_damage_to") val attackHalf: List<GenericObject>,
    @Json(name = "no_damage_from") val defenseZero: List<GenericObject>,
    @Json(name = "no_damage_to") val attackZero: List<GenericObject>,
)
