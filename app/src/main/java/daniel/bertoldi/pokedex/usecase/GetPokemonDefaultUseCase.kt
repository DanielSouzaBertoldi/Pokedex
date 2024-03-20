package daniel.bertoldi.pokedex.usecase

import daniel.bertoldi.pokedex.data.repository.PokedexRepository
import javax.inject.Inject

class GetPokemonDefaultUseCase @Inject constructor(
    private val pokedexRepository: PokedexRepository,
) : GetPokemonUseCase {

    override suspend fun basicData(pokemonId: Int) = pokedexRepository.getBasicPokemons(pokemonId)

    override suspend fun completeData(pokemonId: Int) = pokedexRepository.getCompletePokemon(
        pokemonId
    )
}
