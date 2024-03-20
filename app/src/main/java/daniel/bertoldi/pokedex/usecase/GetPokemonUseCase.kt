package daniel.bertoldi.pokedex.usecase

import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel

interface GetPokemonUseCase {

    suspend fun basicData(pokemonId: Int): PokemonBasicModel

    suspend fun completeData(pokemonId: Int): PokemonCompleteModel
}
