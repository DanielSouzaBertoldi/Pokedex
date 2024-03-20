package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.GenerationData
import daniel.bertoldi.pokedex.domain.model.PokemonModel

interface PokedexRemoteDataSource {

    suspend fun getPokemon(
        pokemonId: Int,
    ): PokemonModel

    suspend fun getNumberOfGenerations(): Int
    suspend fun getGeneration(id: Int): GenerationData
}
