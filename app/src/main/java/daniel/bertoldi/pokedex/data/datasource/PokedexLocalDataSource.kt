package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel

interface PokedexLocalDataSource {

    suspend fun getBasicPokemon(
        pokemonId: Int
    ): PokemonBasicModel

    suspend fun getCompletePokemon(
        pokemonId: Int,
    ): PokemonCompleteModel
}