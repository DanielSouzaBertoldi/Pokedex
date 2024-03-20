package daniel.bertoldi.pokedex.data.repository

import daniel.bertoldi.pokedex.domain.model.PokemonModel

interface PokedexRepository {

    suspend fun getPokemons(
        pokemonId: Int,
    ): PokemonModel
}
