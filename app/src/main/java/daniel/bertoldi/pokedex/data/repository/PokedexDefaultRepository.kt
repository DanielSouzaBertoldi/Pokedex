package daniel.bertoldi.pokedex.data.repository

import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.data.datasource.PokedexLocalDataSource
import daniel.bertoldi.pokedex.data.datasource.PokedexRemoteDataSource
import javax.inject.Inject

class PokedexDefaultRepository @Inject constructor(
    private val remoteDataSource: PokedexRemoteDataSource,
    private val pokemonDao: PokemonDao,
    private val localDataSource: PokedexLocalDataSource,
) : PokedexRepository {

    override suspend fun getPokemons(pokemonId: Int) =
        if (pokemonDao.isPokemonInDatabase(pokemonId)) {
            localDataSource.getPokemon(pokemonId)
        } else {
            remoteDataSource.getPokemon(pokemonId)
        }
}
