package daniel.bertoldi.pokedex.data.api.response

import com.squareup.moshi.Json

data class GenerationsListResponse(
    val count: Int,
)

data class GenerationResponse(
    val id: Int,
    @Json(name = "main_region") val mainRegion: GenerationMainRegionResponse,
    @Json(name = "pokemon_species") val pokemonSpecies: List<GenerationPokemonSpecies>
)

data class GenerationMainRegionResponse(
    val name: String,
)

data class GenerationPokemonSpecies(
    val name: String,
    val url: String,
)