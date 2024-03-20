package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.api.PokeApi
import daniel.bertoldi.pokedex.domain.mapper.PokemonResponseToModelMapper
import javax.inject.Inject

class PokedexDefaultRemoteDataSource @Inject constructor(
    private val pokeApi: PokeApi,
    private val pokemonResponseToModelMapper: PokemonResponseToModelMapper,
) : PokedexRemoteDataSource {

    override suspend fun getPokemon(pokemonId: Int) =
        pokemonResponseToModelMapper.mapFrom(pokeApi.getPokemon(pokemonId))
}
