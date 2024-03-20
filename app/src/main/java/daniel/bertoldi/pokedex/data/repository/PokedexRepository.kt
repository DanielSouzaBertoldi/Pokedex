package daniel.bertoldi.pokedex.data.repository

import daniel.bertoldi.pokedex.domain.model.GenerationData
import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel

interface PokedexRepository {

    suspend fun getBasicPokemons(
        pokemonId: Int,
    ): PokemonBasicModel

    suspend fun getCompletePokemon(
        pokemonId: Int,
    ): PokemonCompleteModel

    suspend fun fetchListOfGenerations(): List<GenerationData>
}
