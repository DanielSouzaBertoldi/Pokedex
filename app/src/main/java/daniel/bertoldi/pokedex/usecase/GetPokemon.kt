package daniel.bertoldi.pokedex.usecase

import daniel.bertoldi.pokedex.data.repository.PokedexRepository
import javax.inject.Inject

class GetPokemon @Inject constructor(
    private val pokedexRepository: PokedexRepository,
) : GetPokemonUseCase {
    override suspend fun invoke(pokemonId: Int) = pokedexRepository.getPokemons(pokemonId)
}