package daniel.bertoldi.pokedex.usecase

import daniel.bertoldi.pokedex.domain.model.PokemonModel

interface GetPokemonUseCase {

    suspend operator fun invoke(pokemonId: Int): PokemonModel
}