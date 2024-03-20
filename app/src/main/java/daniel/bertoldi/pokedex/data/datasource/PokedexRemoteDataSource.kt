package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.domain.model.GenerationData
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel
import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel

interface PokedexRemoteDataSource {

    suspend fun getBasicPokemonInfo(
        pokemonId: Int,
    ): PokemonBasicModel

    suspend fun getCompletePokemonInfo(
        pokemonId: Int,
    ): PokemonCompleteModel

    suspend fun getNumberOfGenerations(): Int
    suspend fun getGeneration(id: Int): GenerationData
}
