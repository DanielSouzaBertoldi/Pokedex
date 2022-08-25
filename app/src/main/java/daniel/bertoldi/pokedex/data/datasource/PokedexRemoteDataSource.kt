package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.domain.model.PokemonModel

interface PokedexRemoteDataSource {

    suspend fun getPokemon(
        pokemonId: Int,
    ): PokemonModel
}