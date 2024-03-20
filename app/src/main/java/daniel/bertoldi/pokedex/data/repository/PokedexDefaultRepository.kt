package daniel.bertoldi.pokedex.data.repository

import daniel.bertoldi.pokedex.data.datasource.PokedexRemoteDataSource
import javax.inject.Inject

class PokedexDefaultRepository @Inject constructor(
    private val remoteDataSource: PokedexRemoteDataSource,
) : PokedexRepository {

    override suspend fun getPokemons(pokemonId: Int) = remoteDataSource.getPokemon(pokemonId)
}
