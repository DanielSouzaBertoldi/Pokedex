package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.GenerationData
import daniel.bertoldi.pokedex.data.api.response.GenerationResponse
import javax.inject.Inject


class GenerationResponseToModelMapper @Inject constructor() {
    fun mapFrom(response: GenerationResponse) = GenerationData(
        id = response.id,
        range =
            response.pokemonSpecies.first().url.getIdFromUrl()..response.pokemonSpecies.last().url.getIdFromUrl(),
        currentPokemons = mutableListOf<Int>().apply {
            for (i in 0..2) { add(response.pokemonSpecies[i].url.getIdFromUrl()) }
        }
    )

    private fun String.getIdFromUrl() =
        this.substringBeforeLast("/").substringAfterLast("/").toInt()
}